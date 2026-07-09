# System Architecture

This document explains the full high-level architecture of Tio-Flix.

## High-level flow

```text
Android App
↓
Auth Layer
↓
Repository Layer
↓
Supabase / Backend API
↓
PostgreSQL Database

Android Player
↓
Media3 ExoPlayer
↓
HLS .m3u8 Video URL
↓
Bunny Stream / Mux CDN

Android Player
↓
Google IMA SDK
↓
Google Ad Manager / VAST Ad Tag
```

## Main systems

### 1. Android App

Responsible for:

- UI screens
- Navigation
- Auth session handling
- Movie browsing
- Player UI
- Ads integration
- Watch progress sync

### 2. Auth Provider

Recommended:

```text
Supabase Auth
```

Responsibilities:

- Email/password login
- Continue with Google
- Password reset
- Session/JWT handling
- User identity

### 3. Database

Recommended:

```text
Supabase PostgreSQL
```

Stores:

- Profiles
- Movies
- Categories
- Watch history
- Favorites
- Continue Watching
- Ad breaks
- Ad events

### 4. Video streaming provider

Recommended options:

```text
Bunny Stream
Mux
```

Responsibilities:

- Video upload
- Video transcoding
- HLS .m3u8 delivery
- CDN delivery
- Adaptive bitrate streaming

### 5. Ads system

Recommended:

```text
Google IMA SDK + Google Ad Manager
```

Responsibilities:

- Pre-roll ads
- Mid-roll ads
- VAST/VMAP ad playback
- Ad event callbacks
- Ad failure handling

## Recommended data flow: Home screen

```text
App opens
↓
Check auth session
↓
Fetch home sections from database/API
↓
Load posters and banners
↓
Display categories and movie rows
```

## Recommended data flow: Movie playback

```text
User taps Watch Now
↓
App fetches movie details
↓
App fetches video_url and ad breaks
↓
Player starts with pre-roll ad
↓
After ad, HLS video starts
↓
Player saves watch progress every few seconds
↓
At mid-roll break, ad plays
↓
Movie resumes from same position
```

## Security boundaries

Android app can contain:

```text
Supabase anon key
Public movie metadata
Public poster URLs
Public ad tag URL if allowed
```

Android app must not contain:

```text
Supabase service role key
Private admin keys
Bunny Stream private API key
Mux secret key
Google service credentials
```

## Production recommendation

Use a backend layer for sensitive operations:

```text
Supabase Edge Functions / Ktor / Spring Boot
```

Use backend for:

- Admin movie creation
- Video provider API calls
- Signed video URL generation
- Ad analytics verification
- Secure reports
