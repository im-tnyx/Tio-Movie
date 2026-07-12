package com.tioflix.app.domain.repository

import com.tioflix.app.domain.model.ContentItem

interface FavoritesRepository {
    suspend fun isFavorite(contentId: String): Result<Boolean>
    suspend fun setFavorite(contentId: String, isFavorite: Boolean): Result<Unit>
    suspend fun getFavorites(limit: Int = 20): Result<List<ContentItem>>
}
