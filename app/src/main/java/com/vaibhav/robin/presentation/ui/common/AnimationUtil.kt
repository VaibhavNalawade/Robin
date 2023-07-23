package com.vaibhav.robin.presentation.ui.common

import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewAnimationUtils
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.splashscreen.SplashScreen

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
@Composable
fun SlideInLeftVisibilityAnimation(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally()+ fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutHorizontally() + shrinkHorizontally () + fadeOut()) {
        content()
    }

}
@Composable
fun SlideInRightVisibilityAnimation(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally {
            with(density) { 360.dp.roundToPx() }
        } + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutHorizontally() + shrinkHorizontally () + fadeOut()) {
        content()
    }

}

fun <T> tweenSpec()= tween<T>(
    durationMillis = 300,
    easing = LinearOutSlowInEasing
)
 fun splashFinishAnimation(splashScreen: SplashScreen) {
    splashScreen.setOnExitAnimationListener { splashScreenView ->
        val x: Int = splashScreenView.view.right / 2
        val y: Int = splashScreenView.view.bottom * 2

        val startRadius = splashScreenView.view.width
            .coerceAtLeast((splashScreenView.view.height)) * 2f
        val endRadius = startRadius / 2f

        val revealAnim = ViewAnimationUtils
            .createCircularReveal(
                splashScreenView.view,
                x,
                y,
                startRadius,
                endRadius
            )
        val animator = ObjectAnimator.ofFloat(
            splashScreenView.view,
            "alpha",
            1f,
            0f
        )
        animator.duration = 600L
        revealAnim.duration = 600L
        revealAnim.doOnEnd { splashScreenView.remove() }
        revealAnim.doOnStart {
            //splashScreenView.iconView.visibility = View.INVISIBLE
            animator.start()
        }
        revealAnim.start()
    }
}