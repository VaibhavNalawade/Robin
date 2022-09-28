package com.vaibhav.robin.presentation.models.state

import com.vaibhav.robin.presentation.UiText

data class DropdownState(
    val text: String = "",
    val selectedIndex:Int=0,
    val errorMessage: UiText? = null,
    val error: Boolean = false
)