package com.rolandl.poc.navigation.screen.splash

class SplashHelper
{

  companion object {

    private var initialized = false

    fun isInitialized(): Boolean =
      initialized

    fun markAsInitialized(isInitialized: Boolean = true)
    {
      initialized = isInitialized
    }

  }

}