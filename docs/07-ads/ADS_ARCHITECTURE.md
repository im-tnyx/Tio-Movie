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

## Anti-mod APK ad protection

It is not possible to guarantee 100% protection against modified APKs because the Android app runs on the user's device. The correct strategy is to avoid trusting only client-side ad logic.

Tio-Flix should use backend-controlled playback access.

```text
App requests movie playback
↓
Backend verifies user session
↓
Backend verifies app/device integrity
↓
Backend sends required ad configuration
↓
App plays required pre-roll ad
↓
App reports ad_completed event
↓
Backend issues short-lived playback token or signed video URL
↓
Player starts movie
↓
At mid-roll checkpoint, backend requires another ad_completed event
↓
Backend allows playback continuation
```

## Rules for stronger ad enforcement

1. Do not keep permanent video URLs in the app.
2. Use short-lived signed playback URLs or playback tokens.
3. Verify required `ad_completed` events on the backend.
4. Store ad events in the database for analytics and abuse detection.
5. Use Play Integrity API to detect modified apps, suspicious devices, or tampered installs.
6. Never put Bunny/Mux private API keys in the Android app.
7. Never put Supabase service role key in the Android app.
8. Do not rely only on client-side checks for ad enforcement.

## Backend-controlled playback token model

Recommended flow:

```text
POST /playback/start
- user_id
- movie_id
- integrity_token

Backend response:
- required_ad_breaks
- playback_session_id
- pre_roll_required
```

After pre-roll:

```text
POST /ads/event
- playback_session_id
- movie_id
- event_type = ad_completed
- break_seconds = 0
```

Then:

```text
POST /playback/token
- playback_session_id
- movie_id

Backend response:
- short_lived_video_url
- expires_at
```

## Mid-roll enforcement

For mid-roll, the app should pause playback at the configured break.

```text
Movie reaches 600 seconds
↓
App pauses movie
↓
App plays mid-roll ad
↓
App reports ad_completed for 600 seconds
↓
Backend validates event
↓
Playback continues
```

If the app is modified to skip the ad, backend analytics should detect missing required ad events.

## Play Integrity API

Use Play Integrity API before issuing sensitive playback tokens.

Backend should check:

```text
App package integrity
App signing certificate integrity
Device integrity
Account/session validity
```

If integrity fails, backend can:

```text
Block playback
Show limited playback
Require app update
Log suspicious session
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

If ad fails because of network/provider issue:

```text
Log ad_error
Apply backend policy
Allow retry or resume depending on policy
Do not create infinite loading loop
```

For production, backend policy should decide whether to allow playback after repeated genuine ad errors.

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
