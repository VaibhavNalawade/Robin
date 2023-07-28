package com.vaibhav.robin.presentation.ui.cart


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.models.state.CartErrorHandler
import com.vaibhav.robin.presentation.models.state.CartUiState
import com.vaibhav.robin.presentation.models.state.CartUiState.EmptyCart
import com.vaibhav.robin.presentation.models.state.CartUiState.Error
import com.vaibhav.robin.presentation.models.state.CartUiState.Loading
import com.vaibhav.robin.presentation.models.state.CartUiState.Success
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.priceFormat
import com.vaibhav.robin.presentation.ui.common.DynamicProperties
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage
import com.vaibhav.robin.presentation.ui.common.ShowFullScreenError
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.ui.common.Summary
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(
    cartUiState: CartUiState,
    onBackNavigation: () -> Unit,
    onRemoveCartItem: (String) -> Unit,
    onCheckout: () -> Unit,
    onBrowse: () -> Unit,
    retry: () -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.cart),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackNavigation,
                        modifier = Modifier.testTag(CartTestTags.BACK_BUTTON)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = stringResource(id = R.string.navigation_back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        BoxWithConstraints(modifier = Modifier.padding(padding)) {

            if (maxWidth < 600.dp) {
                var visibilityAnimation by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(key1 = 0, block = {
                    visibilityAnimation = true
                })
                AnimatedVisibility(
                    visible = visibilityAnimation,
                    enter = slideInVertically(tween(300)) { +100 },
                ) {
                    CompactLayout(
                        cartUiState = cartUiState,
                        retry = retry,
                        onRemoveCartItem = onRemoveCartItem,
                        onCheckout = onCheckout,
                        onBrowse = onBrowse
                    )
                }
            } else
                ExpandedLayout(
                    cartUiState = cartUiState,
                    retry = retry,
                    onRemoveCartItem = onRemoveCartItem,
                    onCheckout = onCheckout,
                    onBrowse = onBrowse
                )
        }
    }
}

@Composable
private fun CompactLayout(
    cartUiState: CartUiState,
    retry: () -> Unit,
    onRemoveCartItem: (String) -> Unit,
    onCheckout: () -> Unit,
    onBrowse: () -> Unit
) {
    when (cartUiState) {
        EmptyCart -> EmptyCart(onBrowse)

        Loading -> Loading()

        is Error -> {
            val errorHandler = remember {
                CartErrorHandler(
                    exception = cartUiState.exception,
                    onSupport = {},
                    onAttemptLater = {},
                    onRetry = retry
                )
            }
            ShowFullScreenError(errorHandler = errorHandler)
        }

        is Success -> CartList(
            cartUiSuccess = cartUiState,
            onRemoveCartItem = onRemoveCartItem,
            endContent = { modifier ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.gird_two)
                ) {
                    Summary(
                        cartItem = cartUiState.cartItems,
                        buttonLabel = {
                            Text(
                                text = stringResource(
                                    R.string.cart_checkout,
                                    priceFormat.format(it.total)
                                )
                            )
                        },
                        textMessage = stringResource(id = R.string.cart_msg),
                        onClick = onCheckout
                    )
                }
            }
        )
    }
}

@Composable
private fun ExpandedLayout(
    cartUiState: CartUiState,
    retry: () -> Unit,
    onRemoveCartItem: (String) -> Unit,
    onCheckout: () -> Unit,
    onBrowse: () -> Unit
) {
    when (cartUiState) {
        EmptyCart -> EmptyCart(onBrowse)

        Loading -> Loading()

        is Error -> {
            val errorHandler = remember {
                CartErrorHandler(
                    exception = cartUiState.exception,
                    onSupport = {},
                    onAttemptLater = {},
                    onRetry = retry
                )
            }
            ShowFullScreenError(errorHandler = errorHandler)
        }

        is Success -> {
            Row(
                modifier = Modifier
                    .widthIn(300.dp, 1500.dp)
            ) {
                Column(
                    Modifier
                        .padding(Dimens.gird_two)
                        .weight(1f)
                ) {
                    CartList(
                        cartUiSuccess = cartUiState,
                        onRemoveCartItem = onRemoveCartItem,
                    )
                }
                Column(
                    Modifier
                        .padding(Dimens.gird_two)
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SpacerVerticalFour()
                    Summary(
                        cartItem = cartUiState.cartItems,
                        buttonLabel = {
                            Text(
                                text = stringResource(
                                    R.string.cart_checkout,
                                    priceFormat.format(it.total)
                                )
                            )
                        },
                        textMessage = stringResource(id = R.string.cart_msg),
                        onClick = onCheckout
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartList(
    cartUiSuccess: Success,
    onRemoveCartItem: (String) -> Unit,
    endContent: @Composable (Modifier) -> Unit = {}
) {
    LazyColumn(
        content = {
            item(key = null) {
                val n = cartUiSuccess.cartItems.size
                Text(
                    modifier = Modifier.padding(horizontal = Dimens.gird_three),
                    text = stringResource(R.string.items_in_your_cart, n),
                    maxLines = 1,
                    style = typography.bodySmall
                )
            }
            items(
                items = cartUiSuccess.cartItems,
                key = { it.cartId },
                itemContent = {
                    CartListItem(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = TweenSpec(300)
                        ),
                        cartItems = it,
                        onRemoveCartItem = onRemoveCartItem
                    )
                }
            )
            item(key = "null") {
                endContent(
                    Modifier.animateItemPlacement(
                        animationSpec = TweenSpec(300)
                    )
                )
            }
        }
    )
}


@Composable
fun CartListItem(
    modifier: Modifier = Modifier,
    cartItems: CartItem,
    onRemoveCartItem: (String) -> Unit
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = cartItems.productName,
                maxLines = 1
            )
        },
        overlineContent = {
            Text(
                text = cartItems.brandName,
                maxLines = 1
            )
        },
        supportingContent = {
            Text(
                text = stringResource(
                    id = R.string.local_price,
                    priceFormat.format(cartItems.price)
                ),
                maxLines = 1
            )
        },
        leadingContent = {
            RobinAsyncImage(
                contentDescription = null,
                model = cartItems.productImage,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
            )
        },
        trailingContent = {
            IconButton(
                modifier = Modifier
                    .testTag(CartTestTags.REMOVE_BUTTON),
                onClick = { onRemoveCartItem(cartItems.cartId) },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.remove_circle),
                        contentDescription = null
                    )
                }
            )
        }
    )
}

@Composable
fun EmptyCart(onBrowse: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.gird_four)
            .verticalScroll(rememberScrollState())
    ) {

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.box_empty_animation))
        val progress by animateLottieCompositionAsState(composition)

        LottieAnimation(
            composition = composition,
            progress = { progress },
            dynamicProperties = DynamicProperties.boxEmpty(),
            modifier = Modifier
                .size(360.dp)
                .testTag(CartTestTags.EMPTY_CART_ANIMATED_ILLUSTRATE)
        )
        SpacerVerticalFour()
        Text(
            text = stringResource(R.string.empty_cart_msg),
            style = typography.titleLarge,
            textAlign = TextAlign.Center
        )
        SpacerVerticalFour()
        Button(
            onClick = onBrowse,
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.shopping_bag),
                    contentDescription = null,
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(R.string.start_shopping))
            }
        )
    }
}
object CartTestTags{
    const val BACK_BUTTON="BackButton"
    const val EMPTY_CART_ANIMATED_ILLUSTRATE="EmptyCartAnimation"
    const val REMOVE_BUTTON="RemoveButton"
    const val TAG="CartCompose"
}

@Preview
@Composable
fun EmptyCartPreview() {
    RobinAppPreview {
        EmptyCart {}
    }
}


