package com.rolandl.poc.navigation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rolandl.poc.navigation.screen.Screen
import com.rolandl.poc.navigation.screen.add.AddScreen
import com.rolandl.poc.navigation.screen.home.HomeScreen
import com.rolandl.poc.navigation.screen.login.LoginHelper
import com.rolandl.poc.navigation.screen.login.LoginScreen
import com.rolandl.poc.navigation.screen.splash.SplashHelper
import com.rolandl.poc.navigation.screen.splash.SplashScreen
import com.rolandl.poc.navigation.ui.theme.POCNavigationTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the application. This activity serves as the entry point and container for the navigation
 * fragment. It handles setting up the toolbar, navigation controller, and action bar behavior.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel: MainActivityViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val navController = rememberNavController().apply {
        addOnDestinationChangedListener { _, destination, _ ->
          viewModel.needsRedirection(destination)?.let {
            navigate(it.route) {
              popUpTo(it.popUpTo) {
                inclusive = it.inclusive
              }
            }
          }
        }
      }

      POCNavigationTheme {
        HexagonalGamesNavHost(
          navHostController = navController,
          viewModel = viewModel
        )
      }
    }
  }

}

@Composable
fun HexagonalGamesNavHost(navHostController: NavHostController, viewModel: MainActivityViewModel) {
  NavHost(
    navController = navHostController,
    startDestination = Screen.Home.route
  ) {
    composable(route = Screen.Splash.route) {
      SplashScreen(
        onInit = {
          navHostController.navigate(viewModel.getCallingRoute() ?: Screen.Home.route) {
            popUpTo(Screen.Splash.route) {
              inclusive = true
            }
          }
        }
      )
    }
    composable(route = Screen.Home.route) {
      HomeScreen(
        onFABClick = {
          navHostController.navigate(Screen.Add.route)
        }
      )
    }
    composable(route = Screen.Add.route) {
      AddScreen(
        onBackClick = { navHostController.navigateUp() },
        onSaveClick = { navHostController.navigateUp() }
      )
    }
    composable(route = Screen.Login.route) {
      LoginScreen(
        onBackClick = { navHostController.navigateUp() },
        onNotificationDisabledClicked = {
          val navigateTo = viewModel.getCallingRoute()

          println("NAVIGATION - After login, navigate to $navigateTo")

          navHostController.navigate(navigateTo ?: Screen.Home.route) {
            popUpTo(Screen.Login.route) {
              inclusive = true
            }
          }
        }
      )
    }
  }
}
