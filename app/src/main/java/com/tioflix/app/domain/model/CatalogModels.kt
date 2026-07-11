package com.tioflix.app.domain.model

data class Movie(
    val id: String,
    val title: String,
    val description: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseYear: Int?,
    val durationMinutes: Int?,
    val maturityRating: String?,
    val language: String?,
    val isFeatured: Boolean
)

data class MovieCategory(
    val id: Long,
    val slug: String,
    val name: String,
    val sortOrder: Int,
    val movies: List<Movie>
)

data class HomeCatalog(
    val featured: Movie?,
    val categories: List<MovieCategory>
)
