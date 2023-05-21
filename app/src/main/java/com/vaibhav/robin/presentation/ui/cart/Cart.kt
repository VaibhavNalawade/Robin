package com.vaibhav.robin.presentation.ui.cart


import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.LayerDrawable
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavController
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.OrderSummary
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.calculateSummary
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import org.xmlpull.v1.XmlPullParser
import java.text.DecimalFormat


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
                    retry = {/*todo*/},
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
            onBrowse=onBrowse
        )
        SpacerVerticalTwo()
        AnimatedVisibility(visible = !(cartItemResponse as? Response.Success)?.data.isNullOrEmpty()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_two)
            ) {
                SummaryCart(
                    cartItem = cartItemResponse as? Response.Success,
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
            AnimatedVisibility(visible = !(cartItemResponse as? Response.Success)?.data.isNullOrEmpty()) {
                SummaryCart(
                    cartItem = cartItemResponse as? Response.Success,
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
    val n = (cartItemResponse as? Response.Success)?.data?.size ?: 0
    Text(
        modifier = Modifier.padding(horizontal = Dimens.gird_three),
        text = stringResource(R.string.items_in_your_cart, n),
        maxLines = 1,
        style = typography.bodySmall
    )
    when (cartItemResponse) {
        is Response.Error ->
            ShowError(
                exception = cartItemResponse.exception,
                retry = retry
            )

        Response.Loading -> Loading()
        is Response.Success -> {
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
private fun SummaryCart(
    cartItem: Response.Success<List<CartItem>>?,
    onClick: () -> Unit,
) {
    val summary = cartItem?.let { calculateSummary(it.data) } ?: OrderSummary()
    val format = DecimalFormat("#,##0.00")
    Surface(
        shape = CardDefaults.shape,
        color = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_2)
    ) {
        Column(modifier = Modifier.padding(Dimens.gird_two)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
                content = {
                    Text(
                        text = stringResource(
                            R.string.cart_checkout,
                            format.format(summary.total)
                        )
                    )
                }
            )
            SpacerVerticalTwo()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.cart_msg),
                textAlign = TextAlign.Center,
                style = typography.bodyMedium
            )
            SpacerVerticalTwo()
            Surface(
                shape = CardDefaults.shape,
                color = colorScheme.surface
            ) {
                Column(modifier = Modifier.padding(Dimens.gird_two)) {
                    Text(
                        text = stringResource(R.string.order_summary),
                        style = typography.titleMedium
                    )
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.total),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                format.format(summary.total)
                            ),
                            style = typography.titleMedium
                        )
                    }
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.subtotal),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                format.format(summary.subTotal)
                            ),
                            style = typography.bodyMedium
                        )
                    }
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.tax),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                format.format(summary.tax)
                            ),
                            style = typography.bodyMedium
                        )
                    }
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.shipping),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                format.format(summary.shippiing)
                            ),
                            style = typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalAnimationGraphicsApi::class)
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
        val pxValue = LocalDensity.current.run { 6.dp.toPx() }
        LaunchedEffect(key1 = composition, block = {
            composition?.layers?.forEach{
                Log.e("Some",it.name)
            }
        })
        val dynamicProperties = rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                property = LottieProperty.STROKE_COLOR,
                value = colorScheme.primary.toArgb(),
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
                value = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_5).toArgb(),
                keyPath = arrayOf(
                    "Layer 8/boxgirl2 Outlines","**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_5).toArgb(),
                keyPath = arrayOf(
                    "Body/boxgirl2 Outlines","**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_5).toArgb(),
                keyPath = arrayOf(
                    "Legs/boxgirl2 Outlines","**"
                )
            )
            ,
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = colorScheme.primaryContainer.toArgb(),
                keyPath = arrayOf(
                    "BOX/boxgirl2 Outlines","**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_5).toArgb(),
                keyPath = arrayOf(
                    "arms/boxgirl2 Outlines","**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_5).toArgb(),
                keyPath = arrayOf(
                    "head/boxgirl2 Outlines","Group 8","**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_5).toArgb(),
                keyPath = arrayOf(
                    "head/boxgirl2 Outlines","Group 7","**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = colorScheme.primary.toArgb(),
                keyPath = arrayOf(
                    "head/boxgirl2 Outlines","Group 11","**"
                )
            ),
        )
        LottieAnimation(
            modifier = Modifier.size(360.dp),
            composition = composition,
            progress = { progress },
            dynamicProperties=dynamicProperties
        )

        SpacerVerticalFour()
        Text(
            text = stringResource(R.string.empty_cart_msg),
            style = typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        SpacerVerticalFour()
        Button(onClick = onBrowse) {
            Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(R.string.browse))

        }
    }
}

@Preview
@Composable
fun EmptyCartPreview() {
    RobinAppPreview {
        EmptyCart {}
    }
}


