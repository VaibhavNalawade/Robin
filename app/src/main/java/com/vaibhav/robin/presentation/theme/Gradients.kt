package com.vaibhav.robin.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Gradients {
    private val gradients = listOf(
        Brush.horizontalGradient(
            colors = listOf(
                Color(0x99ffafbd),
                Color(0x99ffc3a0)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x99ff9a9e),
                Color(0x99fad0c4)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x99ffecd2),
                Color(0x99fcb69f)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x99ff9a9e),
                Color(0x99fecfef)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x99a1c4fd),
                Color(0x99c2e9fb)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x99cfd9df),
                Color(0x99e2ebf0)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x99e2d1c3),
                Color(0x99fdfcfb)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x9966a6ff),
                Color(0x9989f7fe)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x99feada6),
                Color(0x99f5efef)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x99a3bded),
                Color(0x996991c7)
            )
        ), Brush.horizontalGradient(
            colors = listOf(
                Color(0x9993a5cf),
                Color(0x99e4efe9)
            )
        )
    )

    fun getGradient() = gradients[(0 until gradients.size).random()]

}