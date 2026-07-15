package com.tioflix.app.ui.player

import com.tioflix.app.domain.model.SeriesEpisode

data class NextEpisodePrompt(
    val episode: SeriesEpisode,
    val secondsRemaining: Int = 10
)
