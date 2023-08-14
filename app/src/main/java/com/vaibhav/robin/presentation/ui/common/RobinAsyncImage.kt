package com.vaibhav.robin.presentation.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import coil.compose.AsyncImage
import com.vaibhav.robin.presentation.ui.common.placeholder.PlaceholderHighlight
import com.vaibhav.robin.presentation.ui.common.placeholder.placeholder
import com.vaibhav.robin.presentation.ui.common.placeholder.shimmer

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
            .placeholder(
                placeholder.value,
                highlight = PlaceholderHighlight.shimmer()
            )
            .testTag("CartListItemImage"),
        contentScale = contentScale,
        model = model,
        onSuccess = { placeholder.value = false },
        contentDescription = contentDescription
    )
}
