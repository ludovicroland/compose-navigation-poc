package com.rolandl.poc.navigation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val navController = rememberNavController().apply {
        addOnDestinationChangedListener { _, destination, _ ->
          println("NAVIGATION - addOnDestinationChangedListener: ${destination.route}")

          println("NAVIGATION - checking for splashscreen init")

          if(SplashHelper.isInitialized() == false && destination.route != Screen.Splash.route)
          {
            println("NAVIGATION - Splashscreen not initialized, navigating to splashscreen")

            navigate(Screen.Splash.route) {
              popUpTo(destination.route!!) {
                inclusive = true
              }
            }
          }
          else {
            println("NAVIGATION - splashscreen already initialized")

            println("NAVIGATION - checking for log on the AddRoute")

            if(LoginHelper.isLogged() == false && destination.route == Screen.Add.route)
            {
              println("NAVIGATION - Login not done, navigating to login")

              navigate(Screen.Login.route) {
                popUpTo(destination.route!!) {
                  inclusive = true
                }
              }
            }
            else {
              println("NAVIGATION - Login done or not needed")
            }
          }

        }
      }

      POCNavigationTheme {
        HexagonalGamesNavHost(navHostController = navController)
      }
    }
  }

}

@Composable
fun HexagonalGamesNavHost(navHostController: NavHostController) {
  NavHost(
    navController = navHostController,
    startDestination = Screen.Home.route
  ) {
    composable(route = Screen.Splash.route) {
      SplashScreen(
        onInit = {
          navHostController.navigate(Screen.Home.route) {
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
        onBackClick = { navHostController.navigateUp() }
      )
    }
  }
}
