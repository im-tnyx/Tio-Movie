package com.tioflix.app.domain.model

data class PlaybackSession(
    val playbackUrl: String,
    val title: String,
    val startPositionMs: Long = 0L,
    val expiresAt: String? = null
)
