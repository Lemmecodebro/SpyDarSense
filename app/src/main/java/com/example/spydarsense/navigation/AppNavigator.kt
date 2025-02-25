package com.example.spydarsense.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spydarsense.screens.*

@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController) }  // Ensure HomeScreen accepts navController
        composable("processing/{device}") { backStackEntry ->
            val device = backStackEntry.arguments?.getString("device") ?: "Unknown"
            ProcessingScreen(navController, device)
        }
        composable("result/{device}") { backStackEntry ->
            val device = backStackEntry.arguments?.getString("device") ?: "Unknown"
            ResultScreen(navController, device)
        }
    }
}
