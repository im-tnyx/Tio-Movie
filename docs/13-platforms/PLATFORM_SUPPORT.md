# Platform Support

Tio-Flix is planned as a cross-device Android OTT app.

## Supported platforms

```text
Android Mobile: Supported
Android Tablet: Supported
Android TV / Google TV: Supported
Amazon Fire TV / Fire TV Stick: Supported
```

## Future / optional platforms

```text
Chromecast with Google TV: Future
Web: Future
Wear OS: Not planned
Android Auto: Not planned
```

## Shared architecture

The same backend and streaming system should work for all supported platforms.

```text
Android Mobile / Tablet / TV / Fire TV
↓
Supabase Auth
↓
Supabase PostgreSQL
↓
Bunny Stream / Mux HLS video
↓
Media3 ExoPlayer
↓
Google IMA Ads
```

## Mobile requirements

Mobile app should support:

- Touch navigation
- Portrait and landscape screens
- Fullscreen player
- Gesture controls
- Bottom navigation
- Login and Google sign-in
- Movie detail screen
- Continue Watching
- Favorites

## Android TV requirements

Android TV app should support:

- D-pad remote navigation
- Focusable cards and buttons
- 10-foot UI layout
- TV launcher banner
- TV-safe spacing and typography
- Player controls usable by remote
- Back button behavior
- Search with TV keyboard/voice input if available

## Fire TV / Fire TV Stick requirements

Fire TV app should support:

- Fire TV remote navigation
- D-pad focus handling
- Select/play/pause/back buttons
- TV-friendly home rows
- Player controls usable by Fire TV remote
- Amazon Appstore release readiness

## UI strategy

Use shared business logic and separate responsive UI behavior.

```text
Shared:
- AuthRepository
- MovieRepository
- WatchHistoryRepository
- FavoritesRepository
- Player logic
- Supabase data sources

Platform-specific:
- Mobile navigation UI
- TV focus UI
- Fire TV remote behavior
- TV launcher assets
```

## Player strategy

Use Media3 ExoPlayer for all Android platforms.

```text
Mobile: Touch controls + gestures
Android TV: Remote-friendly controls
Fire TV: Remote-friendly controls
```

## Design rule

Mobile UI and TV UI should not be exactly the same. TV screens need larger spacing, bigger cards, clear focus state, and simpler navigation.

## Release targets

```text
Google Play Store: Android mobile/tablet
Google Play Store TV: Android TV / Google TV
Amazon Appstore: Fire TV / Fire TV Stick
```
