# Ads Architecture

This document explains how ads should work in Tio-Flix.

## Recommended ads stack

```text
Google IMA SDK + Google Ad Manager
```

For an OTT/movie app, IMA is better than normal interstitial ads because it is made for video ad playback.

## Ad types

Tio-Flix should support:

```text
Pre-roll: ad before movie starts
Mid-roll: ad during movie playback
Post-roll: optional ad after movie ends
```

Version 1 should support:

```text
Pre-roll
Mid-roll
```

## Ad break examples

```text
0 seconds = pre-roll
600 seconds = mid-roll after 10 minutes
1200 seconds = mid-roll after 20 minutes
```

## Ad data model

Ad breaks can be configured per movie in `movie_ad_breaks` table.

```text
movie_id
break_seconds
ad_tag_url
is_active
```

## Playback flow

```text
User taps Watch Now
↓
Player loads movie
↓
Player loads active ad breaks
↓
Pre-roll ad plays
↓
Movie starts
↓
Player reaches mid-roll position
↓
Movie pauses
↓
Mid-roll ad plays
↓
Movie resumes
```

## Ad events

Track important ad events:

```text
ad_started
ad_completed
ad_skipped
ad_error
ad_clicked
```

Save events in `ad_events` table.

## Failure handling

If ad fails:

```text
Do not block the movie forever
Log ad_error
Resume movie playback
```

## User experience rules

- Show loading while ad is loading.
- Do not allow seeking during required ad playback.
- Resume movie exactly where it paused.
- Do not trigger the same mid-roll multiple times in the same session.
- Keep controls simple during ads.

## Android dependencies

```kotlin
implementation("androidx.media3:media3-exoplayer")
implementation("androidx.media3:media3-ui")
implementation("androidx.media3:media3-exoplayer-hls")
implementation("androidx.media3:media3-exoplayer-ima")
```

## Important policy note

Ad integration must follow Google Ad Manager/AdMob/IMA policies. Do not force invalid clicks, do not hide ads, and do not manipulate ad playback events.
