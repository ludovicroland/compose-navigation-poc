package com.rolandl.poc.navigation.screen.login

class LoginHelper
{

  companion object {

    private var logged = false

    fun isLogged(): Boolean =
      logged

    fun markAsLogged()
    {
      logged = true
    }

  }

}