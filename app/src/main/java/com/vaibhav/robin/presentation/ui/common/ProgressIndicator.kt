package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.presentation.RobinTestTags

@Composable
fun Loading(
    modifier: Modifier = Modifier,
    strokeWidth: Dp =2.dp
    ) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .testTag(RobinTestTags.loading),
        content = {
            CircularProgressIndicator(strokeWidth = strokeWidth)
        }
    )
}