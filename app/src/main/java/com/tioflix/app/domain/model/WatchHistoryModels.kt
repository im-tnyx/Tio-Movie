package com.tioflix.app.domain.model

data class WatchProgress(
    val contentId: String,
    val episodeId: String?,
    val positionMs: Long,
    val durationMs: Long,
    val completed: Boolean
)

data class ContinueWatchingItem(
    val content: ContentItem,
    val episodeId: String?,
    val positionMs: Long,
    val durationMs: Long,
    val progressFraction: Float
)
