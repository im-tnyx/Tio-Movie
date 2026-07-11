package com.tioflix.app.domain.repository

interface AuthRepository {
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
    suspend fun signUpWithEmail(email: String, password: String): Result<Unit>
    suspend fun sendPasswordReset(email: String, redirectUrl: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
    fun hasSession(): Boolean
}
