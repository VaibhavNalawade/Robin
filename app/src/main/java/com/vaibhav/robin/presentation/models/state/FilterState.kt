package com.vaibhav.robin.presentation.models.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.vaibhav.robin.presentation.Order

data class FilterState(
    val brandIndex: MutableState<Int?> = mutableStateOf(null),
    val categoryIndex: MutableState<Int?> = mutableStateOf(null),
    val sortOrder:MutableState<Order?> = mutableStateOf(null)
)
