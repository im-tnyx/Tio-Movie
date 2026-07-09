# Player Architecture

This document defines the Netflix/SonyLIV/Prime-style player plan for Tio-Flix across Android Mobile, Android Tablet, Android TV, and Fire TV.

## Recommended player

```text
Android Media3 ExoPlayer
```

## Architecture rule

Player implementation should follow:

```text
PlayerRoute + PlayerScreen + PlayerViewModel + PlayerUiState + PlayerAction
```

Rules:

- `PlayerScreen` is dumb UI.
- `PlayerRoute` wires ViewModel state, player host, and navigation callbacks.
- `PlayerViewModel` owns player UI state and playback-related presentation logic.
- Player lifecycle should be isolated and easy to release.
- Ad orchestration should follow `ADS_ARCHITECTURE.md` and should not be hidden inside random UI code.

## Player goals

The player should support:

- HLS `.m3u8` playback
- Fullscreen immersive mode
- Play and pause
- Seekbar
- 10-second forward/backward
- Auto-hide controls
- Resume playback
- Pre-roll ads
- Mid-roll ads
- Subtitles
- Audio tracks
- Quality selector
- Next episode
- Skip intro
- Lock controls
- Mobile touch controls
- Android TV D-pad controls
- Fire TV remote controls

## Platform input model

```text
Android Mobile / Tablet
- Touch controls
- Double-tap seek in future
- Gesture controls in future
- Picture-in-picture in future

Android TV / Google TV
- D-pad navigation
- Select button
- Back button
- Play/pause remote key
- Focus-safe controls

Fire TV / Fire TV Stick
- D-pad navigation
- Select button
- Back button
- Play/pause remote key
- Remote-first controls
```

## Player screen layout

### Mobile layout

```text
Top controls
- Back button
- Movie title

Center controls
- Play / Pause
- Back 10 sec
- Forward 10 sec

Bottom controls
- Current time
- Seekbar
- Total duration
- Subtitle button
- Audio button
- Quality button
- Fullscreen/lock option
```

### TV / Fire TV layout

```text
Top controls
- Back action
- Movie title

Center controls
- Back 10 sec
- Play / Pause
- Forward 10 sec

Bottom controls
- Large focusable seekbar
- Current time / total duration
- Focusable subtitle button
- Focusable audio button
- Focusable quality button
```

TV controls must be visibly focusable and usable from a sofa distance.

## Playback flow

```text
PlayerRoute opens
↓
PlayerViewModel loads movie details
↓
PlayerViewModel loads last watch progress
↓
PlayerViewModel loads ad breaks / playback session data
↓
Route prepares ExoPlayer host with HLS URL or playback token flow
↓
Play pre-roll ad if configured
↓
Start movie playback
↓
Save progress periodically
↓
Trigger mid-roll ad at configured break
↓
Resume movie after ad
```

## Resume playback

Save progress to `watch_history` table.

Recommended save timing:

```text
Every 10–15 seconds
On pause
On app background
On player release
Before required mid-roll ad
After ad completes
```

## Forward/backward controls

```kotlin
fun seekBack10(player: ExoPlayer) {
    player.seekTo((player.currentPosition - 10_000).coerceAtLeast(0))
}

fun seekForward10(player: ExoPlayer) {
    player.seekTo((player.currentPosition + 10_000).coerceAtMost(player.duration))
}
```

## Player lifecycle

Release player when screen is destroyed or playback session ends.

```kotlin
DisposableEffect(Unit) {
    onDispose {
        player.release()
    }
}
```

Do not create multiple player instances for the same playback screen unless intentionally required.

## Ads integration

Ads should be handled by Google IMA SDK with Media3 IMA extension.

Ad positions:

```text
0 seconds = pre-roll
600 seconds = mid-roll after 10 minutes
1200 seconds = mid-roll after 20 minutes
```

Ad-supported playback should follow backend-controlled token rules documented in `docs/07-ads/ADS_ARCHITECTURE.md`.

## Player ViewModel responsibilities

```text
Load movie
Load watch progress
Load ad breaks
Track playback position
Save progress
Handle player UI state
Handle errors
Emit one-time player effects if needed
Track focused control state for TV/Fire TV if needed
```

## Player UI state example

```kotlin
data class PlayerUiState(
    val title: String = "",
    val isLoading: Boolean = true,
    val isPlaying: Boolean = false,
    val showControls: Boolean = true,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val isAdPlaying: Boolean = false,
    val focusedControl: PlayerControl? = null,
    val errorMessage: String? = null
)
```

## Player action example

```kotlin
sealed interface PlayerAction {
    data object PlayPauseClicked : PlayerAction
    data object SeekBack10Clicked : PlayerAction
    data object SeekForward10Clicked : PlayerAction
    data class SeekTo(val positionMs: Long) : PlayerAction
    data object SubtitleClicked : PlayerAction
    data object AudioClicked : PlayerAction
    data object QualityClicked : PlayerAction
    data object BackClicked : PlayerAction
}
```

## TV / Fire TV focus rules

- Every visible control must be focusable.
- Focus state must be visually obvious.
- D-pad left/right should work on the seekbar.
- Back button should hide controls first, then exit player if controls are already hidden.
- Play/pause remote key should work even when controls are hidden.
- Do not rely on touch-only gestures for TV.

## Future upgrades

- Double-tap seek gestures
- Brightness gesture
- Volume gesture
- Playback speed
- Picture-in-picture
- Cast support
- DRM
- Offline download
- Dedicated Compose for TV player controls
