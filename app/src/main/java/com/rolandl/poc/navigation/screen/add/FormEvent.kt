package com.rolandl.poc.navigation.screen.add

import androidx.annotation.StringRes
import com.rolandl.poc.navigation.R

/**
 * A sealed class representing different events that can occur on a form.
 */
sealed class FormEvent {
  
  /**
   * Event triggered when the title of the form is changed.
   *
   * @property title The new title of the form.
   */
  data class TitleChanged(val title: String) : FormEvent()
  
  /**
   * Event triggered when the description of the form is changed.
   *
   * @property description The new description of the form.
   */
  data class DescriptionChanged(val description: String) : FormEvent()
  
}

/**
 * A sealed class representing different errors that can occur on a form.
 *
 * Each error holds a resource ID for the corresponding error message string.
 */
sealed class FormError(@StringRes val messageRes: Int) {
  
  /**
   * Error indicating an issue with the form title.
   *
   * The actual error message can be retrieved using the provided resource ID (`R.string.error_title`).
   */
  data object TitleError : FormError(R.string.error_title)
  
}
