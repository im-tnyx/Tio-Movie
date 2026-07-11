package com.tioflix.app.domain.usecase

import com.tioflix.app.domain.repository.ProfileRepository
import javax.inject.Inject

class SyncCurrentProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.syncCurrentUserProfile()
}
