package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.presentation.ui.theme.Values

@Composable
fun DottedButton(
    onClick: () -> Unit,
    content:@Composable  RowScope.()->Unit
) {
    val outline = colorScheme.outline
    Box(
        modifier = Modifier
            .padding(Values.Dimens.gird_two)
    ) {
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val stroke = Stroke(
                        width = 4f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
                    )
                    drawRoundRect(
                        color = outline,
                        style = stroke,
                        cornerRadius = CornerRadius(12.dp.toPx())
                    )
                },
            onClick = onClick,
            content = content
        )
    }
}