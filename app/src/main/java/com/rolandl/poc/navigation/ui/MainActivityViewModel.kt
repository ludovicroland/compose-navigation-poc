package com.rolandl.poc.navigation.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import com.rolandl.poc.navigation.screen.Screen
import com.rolandl.poc.navigation.screen.login.LoginHelper
import com.rolandl.poc.navigation.screen.splash.SplashHelper
import java.util.concurrent.atomic.AtomicReference

class MainActivityViewModel : ViewModel()
{

  data class NavResult(
    val route: String,
    val popUpTo: String,
    val inclusive: Boolean
  )

  private val callingRoute: AtomicReference<String?> = AtomicReference(null)

  fun needsRedirection(destination: NavDestination): NavResult?
  {
    println("NAVIGATION - addOnDestinationChangedListener: ${destination.route}")

    println("NAVIGATION - checking for splashscreen init")

    val result = if(SplashHelper.isInitialized() == false && destination.route != Screen.Splash.route)
    {
      println("NAVIGATION - Splashscreen not initialized, navigating to splashscreen")

      NavResult(
        route = Screen.Splash.route,
        popUpTo = destination.route!!,
        inclusive = true
      )
    }
    else {
      println("NAVIGATION - splashscreen already initialized")

      println("NAVIGATION - checking for log on the AddRoute")

      if(LoginHelper.isLogged() == false && destination.route == Screen.Add.route)
      {
        println("NAVIGATION - Login not done, navigating to login")

        NavResult(
          route = Screen.Login.route,
          popUpTo = destination.route!!,
          inclusive = true
        )
      }
      else {
        println("NAVIGATION - Login done or not needed")
        null
      }
    }

    if (result != null) {
      callingRoute.set(result.popUpTo)
    }

    return result
  }

  fun getCallingRoute(): String? =
    callingRoute.getAndSet(null)

}
