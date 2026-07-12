-- Server-only playback session audit table.

create table if not exists public.playback_sessions (
    id uuid primary key default gen_random_uuid(),
    user_id uuid not null references auth.users(id) on delete cascade,
    content_id uuid not null references public.content(id) on delete cascade,
    episode_id uuid references public.series_episodes(id) on delete cascade,
    platform text not null check (platform in ('android', 'android_tv', 'fire_tv')),
    provider text not null,
    signed_path text not null,
    expires_at timestamptz not null,
    ip_hash text,
    user_agent text,
    created_at timestamptz not null default timezone('utc', now())
);

create index if not exists playback_sessions_user_created_idx
on public.playback_sessions (user_id, created_at desc);

create index if not exists playback_sessions_expiry_idx
on public.playback_sessions (expires_at);

alter table public.playback_sessions enable row level security;

-- No anon/authenticated grants or policies: only trusted server credentials may access this table.
revoke all on public.playback_sessions from anon, authenticated;

comment on table public.playback_sessions is
'Server-only audit log for short-lived signed playback sessions.';
