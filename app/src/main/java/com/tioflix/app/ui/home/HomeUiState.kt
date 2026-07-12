package com.tioflix.app.ui.home

import com.tioflix.app.domain.model.ContinueWatchingItem
import com.tioflix.app.domain.model.HomeCatalog

data class HomeUiState(
    val title: String = "Tio-Flix",
    val subtitle: String = "Browse the latest movies and shows.",
    val isLoading: Boolean = true,
    val catalog: HomeCatalog? = null,
    val continueWatching: List<ContinueWatchingItem> = emptyList(),
    val errorMessage: String? = null
)
