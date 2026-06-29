package com.example.findinglogs.navigation

sealed class Screen(val route: String) {
    object MainScreen: Screen(route = "main_screen")
    object SettingsScreen: Screen(route = "settings_screen")
}