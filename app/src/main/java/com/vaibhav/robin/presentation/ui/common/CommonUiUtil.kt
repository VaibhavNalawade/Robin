package com.vaibhav.robin.presentation.ui.common

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens

@Composable
fun SpaceBetweenContainer(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.SpaceBetween,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        content()
    }
}


@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    image: Any,
    contentScale: ContentScale = ContentScale.Crop,
    borderStroke: BorderStroke = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline
    )
) {
    RobinAsyncImage(
        modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
            .border(
                border = borderStroke,
                shape = CircleShape
            ),
        contentDescription = contentDescription,
        model = image,
        contentScale = contentScale
    )
}

fun Modifier.placeholder(
    visible: Boolean,
    color: Color = Color.Unspecified,
    shape: Shape = RectangleShape,
    highlight: PlaceholderHighlight? = null,
    placeholderFadeTransitionSpec: @Composable Transition.Segment<Boolean>.() -> FiniteAnimationSpec<Float> = { spring() },
    contentFadeTransitionSpec: @Composable Transition.Segment<Boolean>.() -> FiniteAnimationSpec<Float> = { spring() },
): Modifier = composed {
    Modifier.placeholder(
        visible = visible,
        color = if (color.isSpecified) color else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
        shape = shape,
        highlight = highlight,
        placeholderFadeTransitionSpec = placeholderFadeTransitionSpec,
        contentFadeTransitionSpec = contentFadeTransitionSpec,
    )
}

@Composable
fun SpacerHorizontalHalf(): Unit = Spacer(modifier = Modifier.width(Dimens.gird_half))

@Composable
fun SpacerHorizontalOne(): Unit = Spacer(modifier = Modifier.width(Dimens.gird_one))

@Composable
fun SpacerHorizontalTwo(): Unit = Spacer(modifier = Modifier.width(Dimens.gird_two))

@Composable
fun SpacerHorizontalThree(): Unit = Spacer(modifier = Modifier.width(Dimens.gird_three))

@Composable
fun SpacerHorizontalFour(): Unit = Spacer(modifier = Modifier.width(Dimens.gird_four))

@Composable
fun SpacerVerticalOne(): Unit = Spacer(modifier = Modifier.height(Dimens.gird_one))

@Composable
fun SpacerVerticalTwo(): Unit = Spacer(modifier = Modifier.height(Dimens.gird_two))

@Composable
fun SpacerVerticalThree(): Unit = Spacer(modifier = Modifier.height(Dimens.gird_three))

@Composable
fun SpacerVerticalFour(): Unit = Spacer(modifier = Modifier.height(Dimens.gird_four))

@Composable
fun DividerHorizontal(modifier: Modifier = Modifier): Unit =
    Divider(
        modifier = modifier
            .height(1.dp),
        color = MaterialTheme.colorScheme.outline.copy(.3f)
    )




