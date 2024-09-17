package com.rolandl.poc.navigation.screen.splash

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rolandl.poc.navigation.R

@Composable
fun SplashScreen(
  modifier: Modifier = Modifier,
  viewModel: SplashViewModel = hiltViewModel(),
  onInit: () -> Unit
) {
  println("NAVIGATION - SplashScreen")

  LaunchedEffect(Unit) {
    viewModel.init {
      println("NAVIGATION - SplashViewModel init end")
      SplashHelper.markAsInitialized()
      onInit()
    }
  }


  Scaffold(
    modifier = modifier,
  ) { contentPadding ->
    Icon(
      modifier = Modifier.padding(contentPadding).size(200.dp),
      imageVector = Icons.Filled.Build,
      tint = MaterialTheme.colorScheme.onSurface,
      contentDescription = stringResource(id = R.string.contentDescription_notification_icon)
    )
  }
}