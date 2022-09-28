package com.vaibhav.robin.presentation.ui.common

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun SlideInTopVisibilityAnimation(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
        content()
    }

}
fun <T> tweenSpec()= tween<T>(
    durationMillis = 300,
    easing = LinearOutSlowInEasing
)