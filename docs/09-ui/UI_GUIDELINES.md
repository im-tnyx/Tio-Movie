# UI Guidelines

This document defines the visual direction for Tio-Flix.

## Design goal

Tio-Flix should feel like a premium OTT app:

- Dark theme first
- Large movie banners
- Poster rows
- Smooth scrolling
- Clean typography
- Minimal distractions
- Strong focus on content

## Theme

Recommended default:

```text
Dark theme
Black/dark background
High contrast text
Accent color for primary actions
```

## Main screens

```text
SplashScreen
LoginScreen
SignupScreen
HomeScreen
SearchScreen
MovieDetailScreen
PlayerScreen
MyListScreen
ProfileScreen
```

## Home screen layout

```text
Top app bar
↓
Featured hero banner
↓
Continue Watching row
↓
Trending row
↓
Category rows
↓
Recommended row
```

## Movie card

Movie card should include:

```text
Poster image
Rounded corners
Optional progress bar for continue watching
Title only when needed
```

## Movie detail screen

Should include:

```text
Large banner
Poster/title
Description
Release year
Duration
Language
Age rating
Watch Now button
Add to My List button
```

## Player UI

Player should be distraction-free.

Controls:

```text
Back
Title
Play/Pause
Back 10 seconds
Forward 10 seconds
Seekbar
Subtitle
Audio
Quality
Lock
```

## Buttons

Primary button:

```text
Watch Now
Continue Watching
Login
Signup
```

Secondary button:

```text
Add to My List
Trailer
Cancel
```

## Loading states

Use skeleton loading/shimmer for:

```text
Home rows
Movie detail banner
Poster cards
```

Use normal loader for:

```text
Login
Signup
Player preparation
Ad loading
```

## Empty states

Examples:

```text
No movies found
No favorites yet
No watch history yet
```

## Error states

Show clear messages:

```text
Something went wrong
Check your internet connection
Video is not available
Ad failed to load, continuing playback
```

## UX rules

- Keep player controls large enough for thumb use.
- Keep important actions visible.
- Do not overload movie cards with too much text.
- Use smooth transitions between detail and player.
- Login should be simple and fast.
- Continue with Google should be visible on login and signup screens.
