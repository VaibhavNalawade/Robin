package com.vaibhav.robin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.primary.copy(0.05f)),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(strokeWidth = 6.dp)
    }
}