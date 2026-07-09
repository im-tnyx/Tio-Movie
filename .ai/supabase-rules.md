# Supabase Rules

## Supabase role

Supabase is used for:

- Auth
- Profiles
- Movie metadata
- Categories
- Watch history
- Favorites
- Ad breaks
- Ad events

## Security rules

- Use Supabase Auth for identity.
- Use Row Level Security for user-owned tables.
- Do not put service-role keys in Android code.
- Do not put private backend keys in the repository.
- Use Edge Functions or backend services for privileged actions.

## Tables to treat carefully

```text
profiles
watch_history
favorites
ad_events
movie_ad_breaks
```

## Video rule

Do not store production movie files directly in Supabase Storage for OTT streaming. Use Bunny Stream, Mux, or a proper video streaming/CDN provider.

## Admin rule

Admin-only movie creation, video provider API calls, signed URL generation, and playback token generation should happen through a trusted backend or Edge Function.
