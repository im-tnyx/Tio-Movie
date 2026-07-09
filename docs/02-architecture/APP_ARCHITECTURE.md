# App Architecture

Tio-Flix should use a strict, maintainable Android architecture that can scale across Android Mobile, Android Tablet, Android TV, and Fire TV.

## Recommended pattern

```text
Clean Architecture with MVVM in the presentation layer
```

The presentation layer should follow this feature pattern:

```text
Route + Screen + ViewModel + UiState + Action
```

## Core architecture rule

```text
Composable Screens are dumb UI.
```

Screens should render state and emit actions only. Business logic, navigation decisions, repository calls, Supabase calls, player orchestration, and ad decisions must not live inside dumb screens.

## Layers

```text
UI / Presentation
Compose Route + Screen + ViewModel + UiState + Action
↓
Domain
UseCases + Domain Models + Repository Interfaces
↓
Data
Repository Implementations + Data Sources + DTO Mapping
↓
External Systems
Supabase / Edge Functions / Media3 ExoPlayer / Google IMA / Bunny Stream / Mux
```

## UI / Presentation layer

Built with:

```text
Jetpack Compose
```

The presentation layer is split into:

```text
Route
Screen
ViewModel
UiState
Action
Effect/Event when needed
```

### Route responsibilities

A `Route` composable owns feature wiring.

Responsibilities:

- Collect ViewModel state
- Pass state to Screen
- Pass actions/events to ViewModel
- Handle navigation callbacks from the feature boundary
- Connect platform-specific wrappers when needed

A Route may know about ViewModel and navigation.

### Screen responsibilities

A `Screen` composable is dumb UI.

Responsibilities:

- Render `UiState`
- Show loading, empty, error, and success UI
- Emit `Action` callbacks
- No repository calls
- No Supabase calls
- No business decisions
- No direct navigation logic

### ViewModel responsibilities

ViewModel owns presentation logic.

Responsibilities:

- Hold immutable `UiState`
- Accept `Action`
- Call use cases
- Map domain result to UI state
- Emit one-time effects if needed
- Never depend on Android `Context` unless unavoidable and abstracted

## Domain layer

The domain layer is the center of the app.

Responsibilities:

- App-specific business rules
- Use cases
- Domain models
- Repository interfaces

Examples:

```text
SignInWithEmailUseCase
ContinueWithGoogleUseCase
GetHomeSectionsUseCase
GetMovieDetailUseCase
SaveWatchProgressUseCase
ToggleFavoriteUseCase
GetPlaybackSessionUseCase
ReportAdEventUseCase
```

Repository contracts live in domain:

```text
AuthRepository
MovieRepository
WatchHistoryRepository
FavoritesRepository
PlaybackRepository
AdsRepository
```

## Data layer

The data layer implements domain contracts.

Responsibilities:

- Supabase queries
- Edge Function/API calls
- DTOs
- DTO to domain mapping
- Local cache if needed
- Player/ad data adapters when needed

Examples:

```text
SupabaseAuthRepository
SupabaseMovieRepository
SupabaseWatchHistoryRepository
SupabaseFavoritesRepository
PlaybackTokenRepositoryImpl
ImaAdsRepositoryImpl
```

## External systems

External dependencies should stay behind interfaces or data sources.

Examples:

```text
Supabase Auth
Supabase PostgreSQL
Supabase Edge Functions
Media3 ExoPlayer
Google IMA SDK
Bunny Stream / Mux
Play Integrity API
```

## Suggested package structure

```text
com.tioflix.app
├── di
│   ├── AppModule.kt
│   ├── SupabaseModule.kt
│   ├── RepositoryModule.kt
│   └── PlayerModule.kt
├── core
│   ├── config
│   ├── design
│   ├── error
│   ├── navigation
│   ├── network
│   └── utils
├── data
│   ├── auth
│   ├── movie
│   ├── player
│   ├── ads
│   ├── profile
│   └── mapper
├── domain
│   ├── model
│   ├── repository
│   └── usecase
├── ui
│   ├── auth
│   │   ├── LoginRoute.kt
│   │   ├── LoginScreen.kt
│   │   ├── LoginViewModel.kt
│   │   ├── LoginUiState.kt
│   │   └── LoginAction.kt
│   ├── home
│   ├── movie_detail
│   ├── player
│   ├── search
│   └── profile
├── navigation
│   ├── TioFlixNavHost.kt
│   ├── AuthGraph.kt
│   ├── HomeGraph.kt
│   └── PlayerGraph.kt
└── MainActivity.kt
```

## Feature file pattern

Every major feature should use this structure:

```text
FeatureRoute.kt
FeatureScreen.kt
FeatureViewModel.kt
FeatureUiState.kt
FeatureAction.kt
FeatureEffect.kt when needed
```

## State and Action example

```kotlin
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface LoginAction {
    data class EmailChanged(val value: String) : LoginAction
    data class PasswordChanged(val value: String) : LoginAction
    data object SubmitEmailLogin : LoginAction
    data object ContinueWithGoogle : LoginAction
    data object ForgotPasswordClicked : LoginAction
}
```

Screen usage:

```kotlin
@Composable
fun LoginScreen(
    state: LoginUiState,
    onAction: (LoginAction) -> Unit
) {
    // Render state only and call onAction for events.
}
```

Route usage:

```kotlin
@Composable
fun LoginRoute(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onAction = viewModel::onAction
    )
}
```

## Navigation rule

Tio-Flix must use:

```text
Single NavHost with nested graphs
```

Rules:

- One app-level `TioFlixNavHost`
- Feature graphs are nested under the single NavHost
- Screens do not navigate directly
- Routes expose navigation events/callbacks
- ViewModels should not hold NavController
- Repositories must never navigate

## Error handling rule

Repositories should return `Result<T>` or a custom sealed result.

```kotlin
sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val message: String, val throwable: Throwable? = null) : AppResult<Nothing>
}
```

## TV and Fire TV rule

Mobile and TV can share domain/data layers, but UI must adapt to platform needs.

```text
Mobile: touch-first UI
Android TV / Fire TV: D-pad and remote-first UI
```

TV-specific UI must handle:

- Focus state
- D-pad navigation
- Larger spacing
- Remote-friendly player controls
- Back button behavior

## Production checklist

- Use Clean Architecture with MVVM in presentation layer
- Use Route + Screen + ViewModel + UiState + Action per feature
- Keep Compose Screens dumb
- Keep business logic out of Composables
- Use a single NavHost with nested graphs
- Keep ViewModels free from NavController
- Keep UI logic separate from data logic
- Keep player logic separate from movie data logic
- Keep ads logic separate from player UI
- Keep domain layer independent of Supabase and SDKs
- Never expose secret keys in Android code
- Use dependency injection for repositories, use cases, and SDK wrappers
- Update docs when architecture changes
