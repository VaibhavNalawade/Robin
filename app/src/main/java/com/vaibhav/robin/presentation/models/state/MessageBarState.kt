package com.vaibhav.robin.presentation.models.state

import androidx.compose.runtime.*
import com.vaibhav.robin.presentation.UiText

class MessageBarState {
    var success by mutableStateOf<UiText?>(null)
        private set
    var error by mutableStateOf<UiText?>(null)
        private set
    internal var updated by mutableStateOf(false)
        private set

    @Deprecated("Use new UiText object rather than string")
    fun addSuccess(message: String) {
        error = null
        success = UiText.DynamicString(message)
        updated = !updated
    }

    @Deprecated("Use new UiText object rather than string")
    fun addError(message: String) {
        success = null
        error = UiText.DynamicString(message)
        updated = !updated
    }

    fun addSuccess(message: UiText) {
        error = null
        success = message
        updated = !updated
    }

    fun addError(message: UiText) {
        success = null
        error = message
        updated = !updated
    }
}