# Auth Overview

This document explains the recommended authentication flow for Tio Movie.

## Goal

Tio Movie should support:

- Normal email/password authentication
- Continue with Google
- Secure session handling
- User profile creation
- Watch history and favorites linked to logged-in users

## Recommended auth provider

Use one of these options:

### Option 1: Supabase Auth

Best if the app already uses Supabase PostgreSQL for movie data, watch history, favorites, and ad settings.

Benefits:

- Email/password auth
- Google OAuth support
- Built-in JWT session
- Easy relation with database tables
- Row Level Security support

### Option 2: Firebase Auth

Best if the app is already using Firebase services.

Benefits:

- Very easy Google sign-in
- Stable Android SDK
- Good for fast setup

## Recommended for this project

For Tio Movie, use:

```text
Supabase Auth + Supabase PostgreSQL
```

Reason:

- Movie app data is relational
- Users have watch history
- Users have favorites
- Users may later have ad preferences, parental controls, or subscription records
- PostgreSQL works cleanly for these relations

## Auth flow

```text
User opens app
↓
App checks existing session
↓
If session exists → go to Home
↓
If no session → show Login screen
↓
User chooses Email Login or Continue with Google
↓
Auth provider returns session/JWT
↓
App creates or updates profile row
↓
User enters Home screen
```

## Screens needed

```text
LoginScreen
SignupScreen
ForgotPasswordScreen
ProfileSetupScreen
HomeScreen
```

## User profile data

The auth provider stores the secure identity. The app database should store app-specific profile data.

Example profile fields:

```text
id
email
full_name
avatar_url
provider
created_at
updated_at
```

## Security rules

- Never store raw passwords in your own database.
- Let Supabase/Firebase handle passwords.
- Use HTTPS only.
- Store sessions securely.
- Do not expose service-role keys in Android app.
- Use Row Level Security for user-owned data.

## OTT-related user data

After login, user id should connect to:

```text
watch_history
favorites
continue_watching
ratings
ad_events
```
