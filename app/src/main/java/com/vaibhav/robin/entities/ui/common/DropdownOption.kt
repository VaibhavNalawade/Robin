package com.vaibhav.robin.entities.ui.common

import androidx.compose.ui.graphics.vector.ImageVector

data class DropdownOption(
    val options: String,
    val leadingIcon: ImageVector? = null,
    val contentDescription: String? = null
)