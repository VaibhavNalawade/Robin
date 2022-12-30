package com.vaibhav.robin.presentation.ui.common

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import com.vaibhav.robin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandingCard(
    modifier: Modifier,
    onClick: () -> Unit = {},
    content: @Composable (expanded: Boolean) -> Unit
) {
    var cardExpanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (cardExpanded) 180f else 0f
    )
    Card(modifier = modifier.animateContentSize(tweenSpec()), onClick = {
        onClick()
        cardExpanded = !cardExpanded
    }, content = {
        content(cardExpanded)
        Icon(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .rotate(rotationState)
                .animateContentSize(
                    tweenSpec()
                ),
            painter = painterResource(id = R.drawable.cancel),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        SpacerVerticalOne()
    })
}