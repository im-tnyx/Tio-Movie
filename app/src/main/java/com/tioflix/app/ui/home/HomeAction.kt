package com.tioflix.app.ui.home

sealed interface HomeAction {
    data object RetryClicked : HomeAction
    data object LogoutClicked : HomeAction
    data class ContentClicked(val contentId: String) : HomeAction
}
