package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens

@Composable
fun SpaceBetweenContainer(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.SpaceBetween,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
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




