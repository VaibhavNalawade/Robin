package com.vaibhav.robin.presentation.ui.cart


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vaibhav.robin.R
import com.vaibhav.robin.data.PreviewMocks
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.presentation.OrderSummary
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.boxEmptyDynamicProperties
import com.vaibhav.robin.presentation.calculateSummary
import com.vaibhav.robin.presentation.priceFormat
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.common.Summary
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(
    viewModel: CartViewModel,
    navController: NavController,
    cartItems: Response<List<CartItem>>
) {
    LaunchedEffect(
        key1 = false,
        block = {
            if (!viewModel.getAuthState())
                navController.navigate(RobinDestinations.LOGIN_ROUTE) {
                    popUpTo(
                        route = RobinDestinations.CART
                    ) {
                        inclusive = true
                    }
                }
        }
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                    IconButton(onClick = { navController.popBackStack() }) {
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

            if (maxWidth < 600.dp)
                CompactLayout(
                    cartItemResponse = cartItems,
                    retry = {/*todo*/ },
                    onRemoveCartItem = {
                        viewModel.removeCartItem(it)
                    },
                    onCheckout = {
                        navController.navigate(RobinDestinations.CHECKOUT) {
                            popUpTo(RobinDestinations.CART)
                        }
                    },
                    onBrowse = {
                        navController.navigate(RobinDestinations.HOME) {
                            popUpTo(RobinDestinations.HOME)
                        }
                    }
                )
            else
                ExpandedLayout(
                    cartItemResponse = cartItems,
                    retry = {},
                    onRemoveCartItem = {
                        viewModel.removeCartItem(it)
                    },
                    onCheckout = {
                        navController.navigate(RobinDestinations.CHECKOUT) {
                            popUpTo(RobinDestinations.CART)
                        }
                    },
                    onBrowse = {
                        navController.navigate(RobinDestinations.HOME) {
                            popUpTo(RobinDestinations.HOME)
                        }
                    }
                )
        }
    }
}

@Composable
private fun CompactLayout(
    cartItemResponse: Response<List<CartItem>>,
    retry: () -> Unit,
    onRemoveCartItem: (String) -> Unit,
    onCheckout: () -> Unit,
    onBrowse: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        CartList(
            cartItemResponse = cartItemResponse,
            retry = retry,
            onRemoveCartItem = onRemoveCartItem,
            onBrowse = onBrowse
        )
        SpacerVerticalTwo()
        AnimatedVisibility(visible = !(cartItemResponse as? Success)?.data.isNullOrEmpty()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_two)
            ) {
                Summary(
                    cartItem = cartItemResponse as? Success,
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

@Composable
private fun ExpandedLayout(
    cartItemResponse: Response<List<CartItem>>,
    retry: () -> Unit,
    onRemoveCartItem: (String) -> Unit,
    onCheckout: () -> Unit,
    onBrowse: () -> Unit
) {
    Row(
        modifier = Modifier
            .widthIn(300.dp, 1500.dp)
    ) {
        Column(
            Modifier
                .padding(Dimens.gird_two)
                .fillMaxWidth(.60f)
                .verticalScroll(rememberScrollState())
        ) {
            CartList(
                cartItemResponse = cartItemResponse,
                retry = retry,
                onRemoveCartItem = onRemoveCartItem,
                onBrowse = onBrowse
            )
        }
        Column(
            Modifier
                .padding(Dimens.gird_two)
                .widthIn(300.dp, 500.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SpacerVerticalFour()
            AnimatedVisibility(visible = !(cartItemResponse as? Success)?.data.isNullOrEmpty()) {
                Summary(
                    cartItem = cartItemResponse as? Success,
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

@Composable
fun CartList(
    cartItemResponse: Response<List<CartItem>>,
    retry: () -> Unit,
    onRemoveCartItem: (String) -> Unit,
    onBrowse: () -> Unit
) {
    val n = (cartItemResponse as? Success)?.data?.size ?: 0
    Text(
        modifier = Modifier.padding(horizontal = Dimens.gird_three),
        text = stringResource(R.string.items_in_your_cart, n),
        maxLines = 1,
        style = typography.bodySmall
    )
    when (cartItemResponse) {
        is Error ->
            ShowError(
                exception = cartItemResponse.exception,
                retry = retry
            )

        Loading -> Loading()
        is Success -> {
            if (cartItemResponse.data.isNotEmpty())
                cartItemResponse.data.forEach {
                    CartListItem(
                        cartItems = it,
                        onRemoveCartItem = onRemoveCartItem
                    )
                }
            else
                EmptyCart(onBrowse)
        }
    }
}

@Composable
fun CartListItem(
    cartItems: CartItem,
    onRemoveCartItem: (String) -> Unit
) {
    ListItem(
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
                text = stringResource(id = R.string.local_price, cartItems.price),
                maxLines = 1
            )
        },
        leadingContent = {
            RobinAsyncImage(
                modifier = Modifier.size(56.dp),
                contentDescription = null,
                model = cartItems.productImage
            )
        },
        trailingContent = {
            IconButton(
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.gird_four),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.box_empty_animation))
        val progress by animateLottieCompositionAsState(composition)

        LottieAnimation(
            modifier = Modifier.size(360.dp),
            composition = composition,
            progress = { progress },
            dynamicProperties = boxEmptyDynamicProperties()
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
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(R.string.start_shopping))
            }
        )
    }
}

@Preview
@Composable
fun EmptyCartPreview() {
    RobinAppPreview {
        EmptyCart {}
    }
}


