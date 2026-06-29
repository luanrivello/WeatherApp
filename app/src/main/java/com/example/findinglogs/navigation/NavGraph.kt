package com.example.findinglogs.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.findinglogs.view.SettingsView
import com.example.findinglogs.view.WeatherMainScreen
import com.example.findinglogs.viewmodel.MainViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val mainViewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(
            route = Screen.MainScreen.route
        ) {
            WeatherMainScreen(navController, mainViewModel)
        }

        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingsView(navController, onBack = { mainViewModel.refreshForecasts() })
        }
    }
}