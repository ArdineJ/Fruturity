package com.ardine.fruturity.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object Detail : Screen("home/{fruitId}") {
        fun createRoute(fruitId: Long) = "home/$fruitId"
    }
}