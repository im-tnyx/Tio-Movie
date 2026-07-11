package com.tioflix.app.core.config

import com.tioflix.app.BuildConfig

object AppConfig {
    val supabaseUrl: String get() = BuildConfig.SUPABASE_URL
    val supabasePublishableKey: String get() = BuildConfig.SUPABASE_PUBLISHABLE_KEY

    val isSupabaseConfigured: Boolean
        get() = supabaseUrl.isNotBlank() && supabasePublishableKey.isNotBlank()
}
