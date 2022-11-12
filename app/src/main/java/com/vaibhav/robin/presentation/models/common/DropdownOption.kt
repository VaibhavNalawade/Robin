package com.vaibhav.robin.presentation.models.common

import androidx.compose.ui.graphics.vector.ImageVector

data class DropdownOption(
    val options: String,
    val leadingIcon: ImageVector? = null,
    val contentDescription: String? = null
)