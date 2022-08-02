package com.vaibhav.robin.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage


@Composable
fun RobinAsyncImage(
    modifier: Modifier,
    contentDescription: String?,
    model: Any?,
    contentScale: ContentScale = ContentScale.Fit
) {
    val placeholder = remember { mutableStateOf(true) }
    AsyncImage(
        modifier = modifier
            .placeholder(placeholder.value),
        contentScale = contentScale,
        model = model,
        onSuccess = { placeholder.value = false },
        contentDescription = contentDescription
    )
}