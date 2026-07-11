package com.tioflix.app.ui.home

import com.tioflix.app.domain.model.HomeCatalog

data class HomeUiState(
    val title: String = "Tio-Flix",
    val subtitle: String = "Browse the latest movies and shows.",
    val isLoading: Boolean = true,
    val catalog: HomeCatalog? = null,
    val errorMessage: String? = null
)
