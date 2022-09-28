package com.vaibhav.robin.presentation.models.state

import com.vaibhav.robin.presentation.UiText

data class TextFieldState(
    val text: String = "",
    val errorMessage: UiText? = null,
    val error: Boolean = false
)