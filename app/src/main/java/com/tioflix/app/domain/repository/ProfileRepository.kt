package com.tioflix.app.domain.repository

interface ProfileRepository {
    suspend fun syncCurrentUserProfile(): Result<Unit>
}
