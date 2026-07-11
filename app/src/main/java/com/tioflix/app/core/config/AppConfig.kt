package com.tioflix.app.core.config

import com.tioflix.app.BuildConfig

object AppConfig {
    val supabaseUrl: String get() = BuildConfig.SUPABASE_URL
    val supabasePublishableKey: String get() = BuildConfig.SUPABASE_PUBLISHABLE_KEY
    val googleWebClientId: String get() = BuildConfig.GOOGLE_WEB_CLIENT_ID

    val isSupabaseConfigured: Boolean
        get() = supabaseUrl.isNotBlank() && supabasePublishableKey.isNotBlank()

    val isGoogleSignInConfigured: Boolean
        get() = isSupabaseConfigured && googleWebClientId.isNotBlank()
}
