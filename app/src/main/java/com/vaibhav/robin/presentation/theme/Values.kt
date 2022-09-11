package com.vaibhav.robin.presentation.theme

import androidx.compose.ui.unit.dp


sealed class Values{
    object Dimens {
        val gird_quarter = 2.dp
        val gird_half = 4.dp
        val gird_one = 8.dp
        val gird_two = 16.dp
        val gird_three = 24.dp
        val gird_four = 32.dp

        val surface_elevation_1=1.dp
        val surface_elevation_2=3.dp
        val surface_elevation_3=6.dp
        val surface_elevation_4=8.dp
        val surface_elevation_5=12.dp

        val brandingImageSize = 28.dp
        val appbarSize=64.dp
        val bottomSheetOnTop=24.dp

    }
    object CustomColor {
        const val cardSurfaceAlpha = 0.7f
        const val subtextAlpha=0.7f
    }
}





