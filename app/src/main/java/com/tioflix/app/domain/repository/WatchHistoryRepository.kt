package com.tioflix.app.domain.repository

import com.tioflix.app.domain.model.ContinueWatchingItem
import com.tioflix.app.domain.model.WatchProgress

interface WatchHistoryRepository {
    suspend fun getProgress(contentId: String): Result<WatchProgress?>
    suspend fun saveProgress(progress: WatchProgress): Result<Unit>
    suspend fun getContinueWatching(limit: Int = 20): Result<List<ContinueWatchingItem>>
}
