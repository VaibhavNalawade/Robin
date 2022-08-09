package com.vaibhav.robin.entities.ui.state

import com.vaibhav.robin.presentation.UiText

data class TextFieldState(
    val text: String = "",
    val errorMessage: UiText? = null,
    val error: Boolean = false
)