# Coding Rules

## Kotlin rules

- Prefer Kotlin idioms and clear naming.
- Keep functions small and focused.
- Use immutable UI state where possible.
- Avoid business logic inside Composables.
- Handle loading, empty, success, and error states.
- Avoid hardcoded production data.

## Compose rules

- Screens should be mostly dumb UI.
- State should come from ViewModel.
- Events should flow from UI to ViewModel.
- Reusable UI belongs in shared components.
- TV/Fire TV components must be focusable and remote-friendly.

## Architecture rules

- Use MVVM + Repository Pattern.
- Keep repositories independent of UI.
- Keep platform-specific behavior isolated when possible.
- Do not expose secrets in Android code.
- Add docs when architecture changes.

## Player rules

- Release ExoPlayer properly.
- Save watch progress on pause/background/release.
- Do not let ads and content playback fight for player state.
- Keep playback state observable and testable.

## Safety rules

Do not add:

- Piracy scraping
- Unauthorized streaming links
- DRM bypass
- Ad bypass
- Secret keys
- Service-role keys
