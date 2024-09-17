package com.rolandl.poc.navigation.screen

import androidx.navigation.NamedNavArgument

sealed class Screen(
  val route: String,
  val navArguments: List<NamedNavArgument> = emptyList()
) {
  data object Home : Screen("home")

  data object Splash : Screen("splash")
  
  data object Login : Screen("login")

  data object Add : Screen("add")
}