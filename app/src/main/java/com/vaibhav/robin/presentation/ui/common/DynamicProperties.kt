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

    @Composable
    fun trex() = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Chao 5", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Chao 6", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Chao 7", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Chao", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Layer 19", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Layer 20", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Layer 21", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Layer 22", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Layer 23", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Layer 24", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Layer 25", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Layer 26", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Layer 27", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "Bola", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "D_perna", "Group 1", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "D_perna", "Group 2", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "D_perna", "Group 4", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "D_perna", "Group 6", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "D_perna", "Group 8", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "D_unhas_01", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "D_unha_02", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "D_antebraco", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "D_braco", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "rabo", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "rabo", "Group 8", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "Dino_corpo", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "BG 6", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "BG 4", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "BG 3", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "L_antebraco", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "L_antebraco", "Group 1", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "L_unha_02", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "L_unhas_01", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "L_braco", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "L_braco", "Group 5", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "L_braco", "Group 6", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "L_braco", "Group 7", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "L_perna", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "L_perna", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "L_perna", "Group 10", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "L_perna", "Group 12", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "L_perna", "Group 14", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "L_perna", "Group 7", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "L_perna", "Group 8", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "Sobrancelha", "Shape 1", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "nariz_olho", "Group 2", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "nariz_olho", "Group 3", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "cabeca_cima", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "cabeca_cima","Group 5", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "cabeca_cima","Group 6", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "cabeca_cima","Group 7", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "cabeca_cima","Group 8", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.outline.toArgb(),
            keyPath = arrayOf(
                "cabeca_cima","Group 9", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "cabeca_baixo", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "cabeca_baixo 2", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "cabeca_baixo 3", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "cabeca_baixo 9", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "cabeca_baixo 5", "**"
            )
        ),   rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "cabeca_baixo 6", "**"
            )
        ),   rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "cabeca_baixo 7", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "cabeca_baixo 8", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "Corpo", "Dino_corpo", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
              "Corpo",  "Dino_corpo 3", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "Corpo",  "sombra", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "Corpo",  "Dino_corpo 6", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "Corpo",  "Dino_corpo 7", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "Corpo",  "Dino_corpo 8", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "Corpo",  "Dino_corpo 9", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "Corpo",  "Dino_corpo 10", "**"
            )
        )
    )
}
