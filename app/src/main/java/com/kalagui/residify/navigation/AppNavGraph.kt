package com.kalagui.residify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kalagui.residify.ui.screens.Login.Login
import com.kalagui.residify.ui.screens.Register.Registration

enum class ResidifyScreens() {
    Login,
    Registration,
    Home,
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ResidifyScreens.Login.name) {
        composable(ResidifyScreens.Login.name) { Login(navController = navController) }
        composable(ResidifyScreens.Registration.name) { Registration(navController = navController) }
        composable(ResidifyScreens.Home.name) { Registration(navController = navController) }
    }
}

sealed class NavigationEvent {
    data object NavigateToHome : NavigationEvent()
    data object NavigateToRegister : NavigationEvent()
}