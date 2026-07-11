package com.tioflix.app.data.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.tioflix.app.core.config.AppConfig
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

data class GoogleCredentialResult(
    val idToken: String,
    val rawNonce: String
)

@Singleton
class GoogleCredentialProvider @Inject constructor() {
    suspend fun getCredential(context: Context): GoogleCredentialResult {
        check(AppConfig.isGoogleSignInConfigured) {
            "Google sign-in is not configured."
        }

        val rawNonce = UUID.randomUUID().toString()
        val hashedNonce = MessageDigest.getInstance("SHA-256")
            .digest(rawNonce.toByteArray())
            .joinToString("") { "%02x".format(it) }

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(AppConfig.googleWebClientId)
            .setNonce(hashedNonce)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = CredentialManager.create(context).getCredential(
            context = context,
            request = request
        )

        val credential = GoogleIdTokenCredential.createFrom(result.credential.data)
        return GoogleCredentialResult(
            idToken = credential.idToken,
            rawNonce = rawNonce
        )
    }
}
