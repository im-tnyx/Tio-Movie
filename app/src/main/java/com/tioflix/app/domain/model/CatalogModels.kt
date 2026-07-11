package com.tioflix.app.domain.model

enum class ContentType {
    MOVIE,
    SERIES
}

data class ContentItem(
    val id: String,
    val type: ContentType,
    val title: String,
    val description: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseYear: Int?,
    val durationMinutes: Int?,
    val totalSeasons: Int?,
    val maturityRating: String?,
    val language: String?,
    val isFeatured: Boolean
)

data class ContentCategory(
    val id: Long,
    val slug: String,
    val name: String,
    val sortOrder: Int,
    val items: List<ContentItem>
)

data class SeriesSeason(
    val id: String,
    val contentId: String,
    val seasonNumber: Int,
    val title: String?,
    val description: String?,
    val posterUrl: String?,
    val episodes: List<SeriesEpisode>
)

data class SeriesEpisode(
    val id: String,
    val seasonId: String,
    val episodeNumber: Int,
    val title: String,
    val description: String?,
    val thumbnailUrl: String?,
    val durationMinutes: Int
)

data class HomeCatalog(
    val featured: ContentItem?,
    val categories: List<ContentCategory>
)
