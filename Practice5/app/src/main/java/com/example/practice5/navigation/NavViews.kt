package com.example.practice5.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practice5.views.HomeView
import com.example.practice5.views.SplashScreen
import com.example.practice5.R

@Composable
fun NavViews() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "SplashScreen") {
        composable("SplashScreen") {
            SplashScreen(
                modifier = Modifier,
                image = R.raw.welcome,
                navController = navController,
            )
        }
        composable("HomeView") {
            HomeView()
        }
    }
}