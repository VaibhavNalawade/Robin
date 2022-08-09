@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.vaibhav.robin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.vaibhav.robin.R
import com.vaibhav.robin.entities.remote.BannerImage
import com.vaibhav.robin.presentation.UiUtil
import com.vaibhav.robin.presentation.theme.Values
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    bannerImage: List<String?>,
    contentScale:ContentScale,
    urlParam:String,
    onClick: () -> Unit
) {

    val pagerState = rememberPagerState()
    val placeholder = remember { mutableStateOf(true) }

    if (bannerImage.isNotEmpty()) {
        placeholder.value = false
    }

    HorizontalPager(
        modifier = Modifier,
        count = bannerImage.size,
        state = pagerState
    )
    { page ->
        bannerImage[page]?.let {
            Slide(
                url = it+urlParam,
                placeholder = placeholder.value,
                currentPageOffset = calculateCurrentOffsetForPage(page).absoluteValue,
                contentScale,
                onClick = onClick
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.tertiary,
                    inactiveColor = MaterialTheme.colorScheme.tertiaryContainer,
                    indicatorHeight = 6.dp,
                    indicatorWidth = 6.dp,
                    modifier = Modifier
                        .padding(Values.Dimens.gird_four)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(bannerImage: MutableState<List<BannerImage>>, onClick: () -> Unit) {

    val pagerState = rememberPagerState()
    val placeholder = remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        UiUtil.autoscroll(pagerState, this) //ToDo in consider view-model based imp on autoscroll
    }

    if (bannerImage.value.isNotEmpty()) {
        placeholder.value = false
    }

    HorizontalPager(
        modifier = Modifier
            .placeholder(placeholder.value)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .defaultMinSize(),
        count = bannerImage.value.size,
        state = pagerState
    )
    { page ->
        bannerImage.value[page].url?.let {
            Slide(
                url = it,
                placeholder = placeholder.value,
                currentPageOffset = calculateCurrentOffsetForPage(page).absoluteValue,
                onClick = onClick
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.tertiary,
                    inactiveColor = MaterialTheme.colorScheme.tertiaryContainer,
                    indicatorHeight = 6.dp,
                    indicatorWidth = 6.dp,
                    modifier = Modifier
                        .padding(Values.Dimens.gird_two)
                        .align(Alignment.BottomEnd)
                )
            }
        }
    }
}

fun cardGraphicsLayer(graphicsLayerScope: GraphicsLayerScope, pageOffset: Float) =
    with(graphicsLayerScope) {
        //ToDO compose bug detected-ScaleFactor is unspecified
        lerp(
            start = ScaleFactor(.8f, .8f),
            stop = ScaleFactor(1f, 1f),
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).also { scale ->
            scaleX = scale.scaleX
            scaleY = scale.scaleY
        }

        alpha = lerp(
            start = ScaleFactor(.5f, .5f),
            stop = ScaleFactor(1f, 1f),
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).scaleY
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Slide(
    url: String,
    placeholder: Boolean,
    currentPageOffset: Float,
    contentScale: ContentScale= ContentScale.FillBounds,
    onClick: () -> Unit,
    content: @Composable() (BoxScope.() -> Unit) = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        shape = RoundedCornerShape(0.dp),
        interactionSource = interactionSource,
        onClick = onClick,
        modifier = Modifier
            .defaultMinSize(Dp.Infinity, 100.dp)
            .placeholder(placeholder, MaterialTheme.colorScheme.outline)
            .graphicsLayer {
                cardGraphicsLayer(
                    graphicsLayerScope = this,
                    pageOffset = currentPageOffset
                )
            }
    ) {
        Box {
            RobinAsyncImage(
                model = url,
                contentDescription = stringResource(id = R.string.promotional_banner),
                modifier = Modifier.fillMaxWidth(),
                contentScale = contentScale
            )
            content.invoke(this)
        }
    }
}