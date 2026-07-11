package com.tioflix.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tioflix.app.ui.auth.login.LoginRoute
import com.tioflix.app.ui.home.HomeRoute

private object Destinations {
    const val Login = "auth/login"
    const val Signup = "auth/signup"
    const val ForgotPassword = "auth/forgot-password"
    const val Home = "home"
}

@Composable
fun TioFlixNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Login
    ) {
        composable(Destinations.Login) {
            LoginRoute(
                onLoginSuccess = {
                    navController.navigate(Destinations.Home) {
                        popUpTo(Destinations.Login) { inclusive = true }
                    }
                },
                onSignupClick = { navController.navigate(Destinations.Signup) },
                onForgotPasswordClick = { navController.navigate(Destinations.ForgotPassword) }
            )
        }

        composable(Destinations.Signup) {
            PlaceholderScreen("Signup screen foundation")
        }

        composable(Destinations.ForgotPassword) {
            PlaceholderScreen("Forgot password foundation")
        }

        composable(Destinations.Home) {
            HomeRoute()
        }
    }
}

@Composable
private fun PlaceholderScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}
