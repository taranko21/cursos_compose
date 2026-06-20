package com.example.calculator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculator.viewModels.IvaCalculatorViewModel
import com.example.calculator.views.HomeView
import com.example.calculator.views.IvaView

@Composable
fun AppNavigation(viewModel: IvaCalculatorViewModel) {
    // HomeView(),
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home") {
            HomeView(navController)
        }
        composable("IvaView") {
            IvaView(navController)
        }
    }
}