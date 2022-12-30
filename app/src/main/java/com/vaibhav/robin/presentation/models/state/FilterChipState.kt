package com.vaibhav.robin.presentation.models.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class FilterChipState(
    val label:String,
    val selected:Boolean=false,
    val leadingIcon:String?=null
)
