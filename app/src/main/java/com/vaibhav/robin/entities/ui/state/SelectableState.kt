package com.vaibhav.robin.entities.ui.state

import com.vaibhav.robin.ui.UiText

data class SelectableState(
    val text: String = "",
    val errorMessage: UiText? = null,
    val error: Boolean = false
)