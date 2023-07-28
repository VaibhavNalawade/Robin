package com.vaibhav.robin.presentation.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.vaibhav.robin.presentation.ui.theme.Values
@Suppress("SpellCheckingInspection")
object DynamicProperties {
    @Composable
    fun boxEmpty(): LottieDynamicProperties {
        val pxValue = LocalDensity.current.run { 6.dp.toPx() }
        return rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                property = LottieProperty.STROKE_COLOR,
                value = MaterialTheme.colorScheme.primary.toArgb(),
                keyPath = arrayOf(
                    "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.STROKE_WIDTH,
                value = pxValue,
                keyPath = arrayOf(
                    "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                    .toArgb(),
                keyPath = arrayOf(
                    "Layer 8/boxgirl2 Outlines", "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                    .toArgb(),
                keyPath = arrayOf(
                    "Body/boxgirl2 Outlines", "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                    .toArgb(),
                keyPath = arrayOf(
                    "Legs/boxgirl2 Outlines", "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
                keyPath = arrayOf(
                    "BOX/boxgirl2 Outlines", "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                    .toArgb(),
                keyPath = arrayOf(
                    "arms/boxgirl2 Outlines", "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                    .toArgb(),
                keyPath = arrayOf(
                    "head/boxgirl2 Outlines", "Group 8", "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                    .toArgb(),
                keyPath = arrayOf(
                    "head/boxgirl2 Outlines", "Group 7", "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.primary.toArgb(),
                keyPath = arrayOf(
                    "head/boxgirl2 Outlines", "Group 11", "**"
                )
            ),
        )
    }

    @Composable
    fun confirmed() = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "circle", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.tertiary.toArgb(),
            keyPath = arrayOf(
                "stars", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.onPrimary.toArgb(),
            keyPath = arrayOf(
                "check", "**"
            )
        ),
    )
}
