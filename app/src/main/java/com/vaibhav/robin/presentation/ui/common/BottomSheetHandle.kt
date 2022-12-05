package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.presentation.ui.theme.Values

@Composable
fun BottomSheetHandle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Values.Dimens.gird_one),
        contentAlignment = Alignment.Center
    ) {
        Surface(modifier = Modifier
            .height(4.dp)
            .width(32.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.40f),
            shape = MaterialTheme.shapes.extraSmall,
            content = {})
    }
}