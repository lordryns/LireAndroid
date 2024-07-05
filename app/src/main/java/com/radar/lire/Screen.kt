package com.radar.lire

sealed class Screen(val route: String) {
    object LibraryScreen: Screen("library_screen")
    object UpdatesScreen: Screen("updates_screen")
    object TrendingScreen: Screen("trending_screen")
}
