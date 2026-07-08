# Tio Movie

Tio Movie is an Android Kotlin OTT/movie app project. This documentation focuses on a simple and scalable authentication system with normal email login and Continue with Google.

## Auth goal

The app should support two login methods:

1. Normal auth
   - Email and password signup
   - Email and password login
   - Forgot password
   - Logout

2. Continue with Google
   - Google sign-in from Android app
   - Create or reuse user profile
   - Store user identity safely in backend/database

## Recommended stack

```text
Android App: Kotlin + Jetpack Compose
Auth: Supabase Auth or Firebase Auth
Database: Supabase PostgreSQL
Video Streaming: Bunny Stream / Mux
Player: Media3 ExoPlayer
Ads: Google IMA SDK / Google Ad Manager
```

## Documentation

- [Auth Overview](docs/auth-overview.md)
- [Google Sign-In Setup](docs/google-signin-setup.md)
- [Supabase Auth & Tables](docs/supabase-auth-schema.md)
- [Android Implementation Notes](docs/android-auth-implementation.md)

## Important note

Use this system only for legal/licensed content. Authentication protects app access and user data, but it does not replace proper video licensing, DRM, or CDN security.
