package com.vaibhav.robin.presentation.ui.cart


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.common.BottomSheetHandle
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalOne
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalTwo
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Cart(
    viewModel: CartViewModel, navController: NavController
) {
    val subTotal = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = false, block = {
        if (!viewModel.getAuthState())
            navController.navigate(RobinDestinations.LOGIN_ROUTE) {
                popUpTo(
                    route = RobinDestinations.CART
                ) {
                    inclusive = true
                }
            }
        viewModel.launch()
    })
    val bottomSheetState = rememberBottomSheetScaffoldState()
    LaunchedEffect(key1 = true, block = {
        bottomSheetState.bottomSheetState.expand()
    })
    BottomSheetScaffold(
        modifier = Modifier.statusBarsPadding(),
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetPeekHeight = 32.dp,
        sheetContent = { FrontLayout(subTotal) },
        scaffoldState = bottomSheetState,
        topBar = {
            CartAppBar(
                items = (viewModel.cartItem as? Response.Success)?.data?.size ?: 0,
                onBackButton = { navController.popBackStack() }
            )
        },
        content = {
            BackLayout(
                viewModel.cartItem,
                subTotal,
                onErrorOccurred = { viewModel.launch() },
                onRemoveButtonClick = { cartItemId ->
                    viewModel.removeCartItem(cartItemId)
                }
            )
        }
    )
}

@Composable
fun BackLayout(
    cartItem: Response<List<CartItem>>,
    subTotal: MutableState<Int>,
    onRemoveButtonClick: (String) -> Unit,
    onErrorOccurred: () -> Unit
) {
    Surface(color = colorScheme.surface) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            when (val item = cartItem) {
                is Response.Error -> item {
                    ShowError(exception = item.message) {
                        onErrorOccurred.invoke()
                    }
                }

                is Response.Loading -> item {
                    Loading()
                }

                is Response.Success -> if (item.data.isNotEmpty())
                    item.data.forEachIndexed { index, cartItem ->
                        item {
                            CartItem(
                                product = cartItem,
                                variantIndex = index,
                                total = subTotal
                            ) {
                                onRemoveButtonClick(cartItem.cartId)
                            }
                        }
                    }
                else item {
                    EmptyCart()
                }
            }
        }
    }
}

@Composable
fun FrontLayout(subTotal: MutableState<Int>) {
    Surface(
        modifier = Modifier,
        tonalElevation = Dimens.surface_elevation_1,
        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
    ) {
        Column {
            BottomSheetHandle()
            SpacerVerticalOne()
            // FIXME: Calculating cost on client side very bad practice
            SpaceBetweenContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_four)
            ) {
                Text(
                    text = "TOTAL",
                    style = typography.titleMedium.copy(colorScheme.onSurfaceVariant)
                )
                Text(
                    text = "₹ ${(subTotal.value + (subTotal.value * .18) + 100).toInt()}",
                    style = typography.headlineMedium.copy(colorScheme.onSurfaceVariant)
                )
            }
            SpacerVerticalTwo()
            // FIXME: Calculating cost on client side very bad practice
            SpaceBetweenContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_four)
            ) {
                Text(
                    text = "SubTotal",
                    style = typography.titleMedium.copy(colorScheme.onSurfaceVariant)
                )
                Text(
                    text = "₹ ${subTotal.value}",
                    style = typography.titleMedium.copy(colorScheme.onSurfaceVariant)
                )
            }
            SpacerVerticalOne()
            // FIXME: Calculating Shipping cost on client side very bad practice
            SpaceBetweenContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_four)
            ) {
                Text(
                    text = "Shipping",
                    style = typography.titleMedium.copy(colorScheme.onSurfaceVariant)
                )
                Text(
                    text = "₹ 100",
                    style = typography.titleMedium.copy(colorScheme.onSurfaceVariant)
                )
            }
            SpacerVerticalOne()
            // FIXME: Calculating Tax on client side very bad practice
            SpaceBetweenContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_four)
            ) {
                Text(
                    text = "Tax",
                    style = typography.titleMedium.copy(colorScheme.onSurfaceVariant)
                )
                Text(
                    text = "₹ ${(subTotal.value * .18).toInt()}",
                    style = typography.titleMedium.copy(colorScheme.onSurfaceVariant)
                )
            }
            SpacerVerticalTwo()
            Button(modifier = Modifier
                .fillMaxWidth(.8f)
                .align(Alignment.CenterHorizontally),
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = ""
                )
                SpacerHorizontalOne()
                Text(text = "Checkout")
            }
        }
    }
}


@Composable
fun CartAppBar(items: Int, onBackButton: () -> Unit) {
    Surface(tonalElevation = Dimens.surface_elevation_5) {
        Row(
            modifier = Modifier
                .height(Dimens.appbarSize)
                .fillMaxWidth()
                .padding(horizontal = Dimens.gird_one),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onBackButton) {
                Icon(
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = stringResource(id = R.string.close)
                )
            }

            SpacerHorizontalOne()
            Text(
                text = stringResource(id = R.string.cart).uppercase(), style = typography.titleLarge
            )

            SpacerHorizontalOne()
            Text(
                text = "$items Items", style = typography.titleMedium
            )
        }
    }
}


@Composable
fun CartItem(
    product: CartItem,
    variantIndex: Int,
    total: MutableState<Int>,
    onRemoveButtonClick: () -> Unit
) {
    // FIXME: Calculating total price on client side very bad practice
    LaunchedEffect(key1 = true, block = {
        total.value = (total.value + product.price.toInt())
    })
    Card {
        Row(
            modifier = Modifier
                .height(112.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RobinAsyncImage(
                modifier = Modifier.size(112.dp),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                model = product.productImage
            )
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                product.productName?.let {
                    Text(
                        text = it, style = typography.titleMedium
                    )
                }
                SpacerVerticalOne()
                Surface(
                    tonalElevation = Dimens.surface_elevation_1, shape = MaterialTheme.shapes.medium
                ) {
                    Row {
                        RobinAsyncImage(
                            modifier = Modifier.size(24.dp),
                            contentDescription = "",
                            model = product.brand?.url
                        )
                        SpacerHorizontalOne()
                        product.brand?.name?.let { Text(text = it, style = typography.bodyMedium) }
                        SpacerHorizontalTwo()
                        Text(
                            text = "₹ ${product.price}",
                            style = typography.titleMedium
                        )
                        SpacerHorizontalOne()
                    }
                }
                SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.cancel),
                                contentDescription = ""
                            )
                        }
                        Text(text = "1", style = typography.labelLarge)
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.cancel),
                                contentDescription = ""
                            )
                        }
                    }
                    FilledTonalIconButton(onClick = {
                        onRemoveButtonClick()

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "",
                            tint = colorScheme.onSurfaceVariant
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun CartItemLoading() {
    Card {
        Row(
            modifier = Modifier
                .height(112.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(modifier = Modifier.size(112.dp),
                tonalElevation = Dimens.surface_elevation_1,
                content = {})
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Surface(
                    modifier = Modifier
                        .height(24.dp)
                        .fillMaxWidth(.8f),
                    tonalElevation = Dimens.surface_elevation_1,
                    content = {},
                    shape = MaterialTheme.shapes.medium
                )
                SpacerVerticalOne()
                Surface(
                    modifier = Modifier
                        .height(24.dp)
                        .fillMaxWidth(.6f),
                    tonalElevation = Dimens.surface_elevation_1,
                    shape = MaterialTheme.shapes.medium
                ) {}
                SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
                    Box {}
                    Surface(modifier = Modifier.size(32.dp),
                        tonalElevation = Dimens.surface_elevation_1,
                        shape = CircleShape,
                        content = {})
                }


            }
        }
    }
}

@Composable
fun EmptyCart() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.gird_four),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.cancel),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        SpacerVerticalFour()
        Text(
            text = "There's Nothing Here",
            style = typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        SpacerVerticalOne()
        Text(
            text = "Expolar Wide Range of collection", style = typography.bodyLarge
        )

        SpacerVerticalFour()
        Button(onClick = { /* Do something! */ }) {
            Icon(
                Icons.Filled.Warning,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Explorer")

        }
    }
}

