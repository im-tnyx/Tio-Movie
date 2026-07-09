# App Architecture

Tio-Flix should use a clean Android architecture that is easy to maintain and scale.

## Recommended pattern

```text
MVVM + Repository Pattern
```

## Layers

```text
UI Layer
↓
ViewModel Layer
↓
Repository Layer
↓
Data Source Layer
↓
Supabase / Backend / Player / Ads SDK
```

## UI Layer

Built with:

```text
Jetpack Compose
```

Responsibilities:

- Render screens
- Handle user interactions
- Observe ViewModel state
- Show loading/error/success UI

Example screens:

```text
SplashScreen
LoginScreen
SignupScreen
HomeScreen
MovieDetailScreen
PlayerScreen
SearchScreen
ProfileScreen
```

## ViewModel Layer

Responsibilities:

- Hold UI state
- Call repositories
- Validate input
- Convert domain data to UI data
- Handle loading/error states

Example:

```text
AuthViewModel
HomeViewModel
MovieDetailViewModel
PlayerViewModel
SearchViewModel
ProfileViewModel
```

## Repository Layer

Responsibilities:

- Hide data source details
- Provide clean functions to ViewModels
- Combine local and remote data if needed

Example:

```text
AuthRepository
MovieRepository
PlayerRepository
WatchHistoryRepository
FavoritesRepository
AdsRepository
```

## Data source layer

Responsibilities:

- Supabase queries
- API calls
- Local cache
- Player events
- Ads events

Example:

```text
SupabaseAuthDataSource
SupabaseMovieDataSource
SupabaseWatchHistoryDataSource
ImaAdsDataSource
```

## Suggested package structure

```text
com.tioflix.app
├── core
│   ├── config
│   ├── design
│   ├── navigation
│   ├── network
│   └── utils
├── data
│   ├── auth
│   ├── movie
│   ├── player
│   ├── ads
│   └── profile
├── domain
│   ├── model
│   ├── repository
│   └── usecase
├── ui
│   ├── auth
│   ├── home
│   ├── movie_detail
│   ├── player
│   ├── search
│   └── profile
└── MainActivity.kt
```

## State handling

Use simple UI state classes:

```kotlin
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
```

## Navigation rule

Do not navigate directly from repository. Navigation should be handled by UI/ViewModel events.

## Error handling rule

Repositories should return `Result<T>` or a custom sealed result.

```kotlin
sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val message: String, val throwable: Throwable? = null) : AppResult<Nothing>
}
```

## Production checklist

- Keep UI logic separate from data logic
- Keep player logic separate from movie data logic
- Keep ads logic separate from player UI
- Never expose secret keys in Android code
- Use dependency injection when project grows
