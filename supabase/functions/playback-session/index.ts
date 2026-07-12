import { createClient } from 'npm:@supabase/supabase-js@2.95.0'

const JSON_HEADERS = {
  'Content-Type': 'application/json; charset=utf-8',
  'Cache-Control': 'no-store',
}

const MAX_SESSIONS_PER_MINUTE = 8
const DEFAULT_TTL_SECONDS = 300

type PlaybackRequest = {
  content_id?: string
  episode_id?: string | null
  platform?: 'android' | 'android_tv' | 'fire_tv'
}

function json(status: number, body: Record<string, unknown>): Response {
  return new Response(JSON.stringify(body), { status, headers: JSON_HEADERS })
}

function getDefaultKey(envName: string): string {
  const raw = Deno.env.get(envName)
  if (!raw) throw new Error(`Missing ${envName}`)
  const parsed = JSON.parse(raw) as Record<string, string>
  const value = parsed.default
  if (!value) throw new Error(`Missing default key in ${envName}`)
  return value
}

function base64Url(bytes: Uint8Array): string {
  let binary = ''
  for (const byte of bytes) binary += String.fromCharCode(byte)
  return btoa(binary).replaceAll('+', '-').replaceAll('/', '_').replaceAll('=', '')
}

async function hmacSha256(secret: string, value: string): Promise<string> {
  const key = await crypto.subtle.importKey(
    'raw',
    new TextEncoder().encode(secret),
    { name: 'HMAC', hash: 'SHA-256' },
    false,
    ['sign'],
  )
  const signature = await crypto.subtle.sign('HMAC', key, new TextEncoder().encode(value))
  return base64Url(new Uint8Array(signature))
}

async function sha256Hex(value: string): Promise<string> {
  const digest = await crypto.subtle.digest('SHA-256', new TextEncoder().encode(value))
  return Array.from(new Uint8Array(digest))
    .map((byte) => byte.toString(16).padStart(2, '0'))
    .join('')
}

Deno.serve(async (req: Request) => {
  try {
    if (req.method !== 'POST') return json(405, { error: 'Method not allowed.' })

    const authHeader = req.headers.get('Authorization') ?? ''
    if (!authHeader.startsWith('Bearer ')) return json(401, { error: 'Missing bearer token.' })

    const token = authHeader.slice('Bearer '.length).trim()
    if (!token) return json(401, { error: 'Missing bearer token.' })

    const supabaseUrl = Deno.env.get('SUPABASE_URL')
    if (!supabaseUrl) throw new Error('Missing SUPABASE_URL')

    const publishableKey = getDefaultKey('SUPABASE_PUBLISHABLE_KEYS')
    const secretKey = getDefaultKey('SUPABASE_SECRET_KEYS')

    const userClient = createClient(supabaseUrl, publishableKey, {
      global: { headers: { Authorization: authHeader } },
      auth: { persistSession: false, autoRefreshToken: false },
    })
    const adminClient = createClient(supabaseUrl, secretKey, {
      auth: { persistSession: false, autoRefreshToken: false },
    })

    const { data: userData, error: userError } = await userClient.auth.getUser(token)
    const user = userData.user
    if (userError || !user) return json(401, { error: 'Invalid or expired session.' })

    const body = (await req.json()) as PlaybackRequest
    const contentId = body.content_id?.trim()
    const episodeId = body.episode_id?.trim() || null
    const platform = body.platform ?? 'android'

    if (!contentId) return json(400, { error: 'content_id is required.' })
    if (!['android', 'android_tv', 'fire_tv'].includes(platform)) {
      return json(400, { error: 'Unsupported platform.' })
    }

    const minuteAgo = new Date(Date.now() - 60_000).toISOString()
    const { count, error: rateError } = await adminClient
      .from('playback_sessions')
      .select('id', { count: 'exact', head: true })
      .eq('user_id', user.id)
      .gte('created_at', minuteAgo)

    if (rateError) throw rateError
    if ((count ?? 0) >= MAX_SESSIONS_PER_MINUTE) {
      return json(429, { error: 'Too many playback requests. Try again shortly.' })
    }

    const { data: content, error: contentError } = await adminClient
      .from('content')
      .select('id,title,content_type,stream_key,is_published')
      .eq('id', contentId)
      .eq('is_published', true)
      .single()

    if (contentError || !content) return json(404, { error: 'Published content not found.' })

    let title = content.title as string
    let streamKey = content.stream_key as string | null

    if (content.content_type === 'SERIES') {
      if (!episodeId) return json(400, { error: 'episode_id is required for series playback.' })

      const { data: episode, error: episodeError } = await adminClient
        .from('series_episodes')
        .select('id,title,stream_key,is_published,series_seasons!inner(content_id,is_published)')
        .eq('id', episodeId)
        .eq('is_published', true)
        .eq('series_seasons.content_id', contentId)
        .eq('series_seasons.is_published', true)
        .single()

      if (episodeError || !episode) return json(404, { error: 'Published episode not found.' })
      title = `${content.title} — ${episode.title}`
      streamKey = episode.stream_key as string | null
    } else if (episodeId) {
      return json(400, { error: 'episode_id is only valid for series content.' })
    }

    if (!streamKey) return json(409, { error: 'Playback source is not configured.' })

    const cdnBaseUrl = (Deno.env.get('PLAYBACK_CDN_BASE_URL') ?? '').replace(/\/$/, '')
    const signingSecret = Deno.env.get('PLAYBACK_SIGNING_SECRET') ?? ''
    if (!cdnBaseUrl.startsWith('https://') || !signingSecret) {
      throw new Error('Playback CDN signing secrets are not configured.')
    }

    const ttlSeconds = Math.min(
      Math.max(Number(Deno.env.get('PLAYBACK_URL_TTL_SECONDS') ?? DEFAULT_TTL_SECONDS), 60),
      900,
    )
    const sessionId = crypto.randomUUID()
    const expiresUnix = Math.floor(Date.now() / 1000) + ttlSeconds
    const expiresAt = new Date(expiresUnix * 1000).toISOString()
    const normalizedPath = `/${streamKey.replace(/^\/+/, '')}`
    const signedValue = `${normalizedPath}:${expiresUnix}:${user.id}:${sessionId}`
    const signature = await hmacSha256(signingSecret, signedValue)
    const playbackUrl = new URL(`${cdnBaseUrl}${normalizedPath}`)
    playbackUrl.searchParams.set('exp', String(expiresUnix))
    playbackUrl.searchParams.set('uid', user.id)
    playbackUrl.searchParams.set('sid', sessionId)
    playbackUrl.searchParams.set('sig', signature)

    const forwardedFor = req.headers.get('x-forwarded-for')?.split(',')[0]?.trim() ?? ''
    const ipHash = forwardedFor ? await sha256Hex(`${forwardedFor}:${signingSecret}`) : null

    const { error: auditError } = await adminClient.from('playback_sessions').insert({
      id: sessionId,
      user_id: user.id,
      content_id: contentId,
      episode_id: episodeId,
      platform,
      provider: 'hmac-query-v1',
      signed_path: normalizedPath,
      expires_at: expiresAt,
      ip_hash: ipHash,
      user_agent: req.headers.get('user-agent')?.slice(0, 500) ?? null,
    })
    if (auditError) throw auditError

    return json(200, {
      playback_url: playbackUrl.toString(),
      title,
      start_position_ms: 0,
      expires_at: expiresAt,
      session_id: sessionId,
    })
  } catch (error) {
    console.error(error)
    return json(500, { error: 'Unable to create playback session.' })
  }
})
