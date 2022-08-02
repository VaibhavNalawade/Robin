package com.vaibhav.robin.entities.ui.state

import com.vaibhav.robin.ui.UiText

data class DropdownState(
    val text: String = "",
    val selectedIndex:Int=0,
    val errorMessage: UiText? = null,
    val error: Boolean = false
)