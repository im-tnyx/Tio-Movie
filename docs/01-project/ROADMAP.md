# Roadmap

This roadmap defines the recommended development phases for Tio-Flix.

Tio-Flix targets:

```text
Android Mobile
Android Tablet
Android TV / Google TV
Amazon Fire TV / Fire TV Stick
```

## Phase 0: Project Foundation

Goal: create the clean base before feature work starts.

- Android project setup
- Kotlin + Jetpack Compose setup
- Clean Architecture package structure
- Route + Screen + ViewModel + UiState + Action pattern
- Single NavHost with nested graphs
- Theme setup
- Environment configuration
- Dependency injection setup
- Basic error/result handling
- Platform detection strategy for mobile/tablet/TV/Fire TV

## Phase 1: Authentication

Goal: stable login before building OTT features.

- Splash session check
- Login Route/Screen/ViewModel/UiState/Action
- Signup Route/Screen/ViewModel/UiState/Action
- Forgot password screen
- Supabase Auth setup
- Continue with Google
- Profile sync after login
- Secure logout
- Auth navigation graph

## Phase 2: Home + Navigation

Goal: users can browse legal movie content.

- Home graph
- Home Route/Screen/ViewModel/UiState/Action
- Home hero banner
- Continue Watching row
- Trending row
- Category rows
- Movie cards
- Movie detail page
- Search screen
- Favorites / My List entry points
- Mobile navigation UI
- TV/Fire TV focusable navigation UI

## Phase 3: Database + Content Contracts

Goal: stable Supabase data model.

- Profiles table
- Movies table
- Categories table
- Movie categories table
- Watch history table
- Favorites table
- Movie ad breaks table
- Ad events table
- RLS policies
- Public read policies for active movies/categories
- Edge Function/backend plan for sensitive operations

## Phase 4: Player Core

Goal: stable HLS playback on mobile and TV devices.

- Media3 ExoPlayer setup
- HLS `.m3u8` playback
- Player Route/Screen/ViewModel/UiState/Action
- Fullscreen immersive mode
- Play/pause
- Seekbar
- 10-second forward/backward
- Resume playback
- Player lifecycle handling
- Save progress on pause/background/release
- Android TV remote controls
- Fire TV remote controls
- D-pad focus behavior

## Phase 5: Ads

Goal: ad-supported OTT playback.

- Google IMA SDK setup
- Pre-roll ads
- Mid-roll ads
- Ad break configuration
- Ad analytics events
- Backend playback session design
- Short-lived playback token/signed URL design
- Play Integrity API planning
- Fallback behavior when ad fails
- TV/Fire TV ad control behavior

## Phase 6: Advanced Player Features

- Subtitles
- Audio tracks
- Quality selector
- Skip intro
- Next episode
- Lock controls
- Picture-in-picture for mobile
- Cast support planning

## Phase 7: User Features

- Watch history
- Continue Watching row
- Favorites / My List
- Profile screen
- Recently watched
- User preferences
- Platform-specific settings if needed

## Phase 8: Admin & Backend

- Admin movie upload flow
- Category management
- Ad break management
- Reports and analytics
- Secure backend actions
- Supabase Edge Functions
- Video provider private API isolation
- Playback token generation

## Phase 9: Platform Release

- Android mobile QA
- Android tablet QA
- Android TV QA
- Fire TV / Fire TV Stick QA
- Crash reporting
- Performance optimization
- Play Store mobile assets
- Play Store TV assets
- Amazon Appstore assets
- Privacy policy
- Production build

## Future features

- Offline downloads
- DRM
- Parental controls
- Multi-profile support
- Push notifications
- Recommendation engine
- Chromecast with Google TV optimization
- Web app
