package com.rolandl.poc.navigation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing user settings, specifically notification preferences.
 */
class SplashViewModel : ViewModel()
{

  fun init(function: () -> Unit)
  {
    println("NAVIGATION - SplashViewModel init call")

    viewModelScope.launch {
      delay(2000)
      function()
    }
  }

}
