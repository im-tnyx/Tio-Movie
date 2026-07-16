package com.tioflix.app.domain.repository

import com.tioflix.app.domain.model.ContentItem
import com.tioflix.app.domain.model.HomeCatalog
import com.tioflix.app.domain.model.SeriesSeason

interface CatalogRepository {
    suspend fun getHomeCatalog(): Result<HomeCatalog>
    suspend fun getContent(contentId: String): Result<ContentItem>
    suspend fun getSeriesSeasons(contentId: String): Result<List<SeriesSeason>>
    suspend fun searchContent(query: String, limit: Int = 30): Result<List<ContentItem>>
}
