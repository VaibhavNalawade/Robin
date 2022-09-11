package com.vaibhav.robin.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.widget.Placeholder
import coil.compose.AsyncImage
import com.vaibhav.robin.R


@Composable
fun RobinAsyncImage(
    modifier: Modifier,
    contentDescription: String?,
    model: Any?,
    contentScale: ContentScale = ContentScale.Fit
) {
    val placeholder = remember { mutableStateOf(true) }
    AsyncImage(
        modifier = modifier.placeholder(placeholder.value),
        contentScale = contentScale,
        model = model,
        onSuccess = { placeholder.value = false },
        contentDescription = contentDescription
    )
}
