# Coding Rules

## Kotlin rules

- Prefer Kotlin idioms and clear naming.
- Keep functions small and focused.
- Use immutable UI state.
- Handle loading, empty, success, and error states.
- Avoid hardcoded production data.
- Prefer explicit models over loose maps for app data.

## Compose rules

- Screens must be 100% dumb UI.
- Screens render `UiState` and emit `Action` only.
- State must be hoisted to ViewModel via Route.
- Events must flow from UI to ViewModel through sealed Actions.
- Do not call repositories, Supabase, APIs, ExoPlayer setup, or IMA setup directly from dumb Screens.
- Reusable UI belongs in shared components.
- TV/Fire TV components must be focusable and remote-friendly.

## Architecture rules

- Strictly follow Clean Architecture with MVVM in the presentation layer.
- Use `Route + Screen + ViewModel + UiState + Action` in every feature.
- Keep Compose Screens dumb.
- Keep repositories independent of UI.
- Keep domain layer independent of Supabase, Media3, IMA, and platform SDKs.
- Use use cases for business logic.
- Repository interfaces belong in domain.
- Repository implementations belong in data.
- Single source of truth for navigation must be the app NavHost.
- Use a single NavHost with nested graphs.
- ViewModels must not hold `NavController`.
- Repositories must never navigate.
- Keep platform-specific behavior isolated when possible.
- Do not expose secrets in Android code.
- Add docs when architecture changes.

## Navigation rules

- Use one app-level `TioFlixNavHost`.
- Use nested graphs for auth, home, player, profile, and platform-specific flows.
- Route composables can connect ViewModel state to Screen and trigger navigation callbacks.
- Screen composables must not know about NavController.

## Player rules

- Release ExoPlayer properly.
- Save watch progress on pause/background/release.
- Keep player lifecycle out of dumb Screens.
- Keep playback state observable and testable.
- Do not let ads and content playback fight for player state.
- Use documented playback/ad session flow for ad-supported playback.

## Ads rules

- Do not implement ad bypass.
- Use Google IMA for pre-roll and mid-roll video ads.
- Required ad logic should be enforced by backend playback sessions, not only by app-side checks.
- Do not keep permanent private playback URLs in the Android app.
- Do not store ad account secrets in source code.

## Supabase rules

- Android app may use the public anon key only.
- Never put the service-role key in Android code.
- Use RLS for user-owned data.
- Use Edge Functions/backend for sensitive operations.
- Do not imply a schema or migration changed unless the SQL is included.

## Platform rules

- Android Mobile and TV/Fire TV may share domain and data layers.
- TV/Fire TV UI must be remote-first and focus-safe.
- Do not assume touch input exists on TV screens.
- Player controls must work with D-pad/select/back/play/pause controls.

## Safety rules

Do not add:

- Piracy scraping
- Unauthorized streaming links
- DRM bypass
- Ad bypass
- Secret keys
- Service-role keys
- Private video provider API keys
- Private ad provider credentials
