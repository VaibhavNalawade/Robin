package com.vaibhav.robin.presentation.models.state

import androidx.compose.runtime.*

class MessageBarState {
    var success by mutableStateOf<String?>(null)
        private set
    var error by mutableStateOf<String?>(null)
        private set
    internal var updated by mutableStateOf(false)
        private set

    fun addSuccess(message: String) {
        error = null
        success = message
        updated = !updated
    }

    fun addError(message: String) {
        success = null
        error = message
        updated = !updated
    }
}