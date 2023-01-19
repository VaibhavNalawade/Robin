package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.presentation.RobinAppPreview

@Composable
fun ToggleFilterButton(
    checked: Boolean,
    changed: (Boolean) -> Unit,
    content:@Composable ()-> Unit
) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .clickable { changed(!checked) },
        color = if (checked) colorScheme.primary else colorScheme.surfaceVariant,
        contentColor = if (checked) colorScheme.onPrimary else colorScheme.onSurfaceVariant,
        content = {
            Box(
                contentAlignment = Alignment.Center,
                content = {
                    content()
                }
            )
        }
    )
}


    @Preview
    @Composable
    fun ToggleFilterButtonPreview() {
        RobinAppPreview {
         //   ToggleFilterButton(checked = false,{})
        }
    }