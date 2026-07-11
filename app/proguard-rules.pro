# Tio-Flix app-specific R8/ProGuard rules.

# Required by Android Credential Manager Play Services integration.
-if class androidx.credentials.CredentialManager
-keep class androidx.credentials.playservices.** {
    *;
}
