# API Specification

This document defines the initial API/data contract for Tio-Flix.

## API strategy

Tio-Flix can start with direct Supabase client queries from Android for simple public data and user-owned data.

Use backend/Edge Functions for sensitive operations.

## Public data

Public readable data:

```text
movies
categories
movie_categories
```

## User-owned data

Protected by auth session and Row Level Security:

```text
profiles
watch_history
favorites
ad_events
```

## Home data

### Get featured movies

Source:

```text
movies where is_featured = true and is_active = true
```

Response model:

```json
{
  "id": "movie-id",
  "title": "Movie Title",
  "description": "Movie description",
  "poster_url": "https://...",
  "banner_url": "https://...",
  "duration_seconds": 7200,
  "release_year": 2026,
  "age_rating": "U/A 13+"
}
```

### Get movies by category

Source:

```text
categories + movie_categories + movies
```

## Movie detail

Required fields:

```json
{
  "id": "movie-id",
  "title": "Movie Title",
  "description": "Movie description",
  "poster_url": "https://...",
  "banner_url": "https://...",
  "video_url": "https://.../master.m3u8",
  "trailer_url": "https://...",
  "duration_seconds": 7200,
  "release_year": 2026,
  "age_rating": "U/A 13+",
  "language": "Hindi"
}
```

## Watch history

### Save progress

Fields:

```json
{
  "movie_id": "movie-id",
  "progress_seconds": 430,
  "duration_seconds": 7200,
  "completed": false
}
```

Rule:

```text
One row per user + movie
Use upsert
```

## Favorites

### Add favorite

```json
{
  "movie_id": "movie-id"
}
```

### Remove favorite

Delete where:

```text
user_id = auth.uid()
movie_id = selected movie id
```

## Ad breaks

### Get ad breaks for movie

Response:

```json
[
  {
    "break_seconds": 0,
    "ad_tag_url": "https://..."
  },
  {
    "break_seconds": 600,
    "ad_tag_url": "https://..."
  }
]
```

## Ad event tracking

### Insert ad event

```json
{
  "movie_id": "movie-id",
  "event_type": "ad_started",
  "break_seconds": 600,
  "ad_tag_url": "https://..."
}
```

## Error model

Use a consistent app error model:

```kotlin
data class AppError(
    val code: String,
    val message: String
)
```

Common error codes:

```text
AUTH_REQUIRED
NETWORK_ERROR
MOVIE_NOT_FOUND
VIDEO_NOT_AVAILABLE
AD_LOAD_FAILED
UNKNOWN_ERROR
```

## Backend-only operations

Do not run these directly from Android:

```text
Create/update/delete movies
Create signed video playback URLs
Call Bunny/Mux admin APIs
Access service role key
Generate reports with private data
```
