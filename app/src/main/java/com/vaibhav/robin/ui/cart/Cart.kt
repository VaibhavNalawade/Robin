package com.vaibhav.robin.ui.cart

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Store
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.RobinAppPreviewScaffold
import com.vaibhav.robin.entities.remote.CartItems
import com.vaibhav.robin.ui.common.*
import com.vaibhav.robin.ui.theme.Values.Dimens


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(navController: NavController, snackBarHostState: SnackbarHostState) {

    val viewModel: CartViewModel = viewModel()
    val cartItemsUiStateState = viewModel.cartItemsUiState.collectAsState().value
    val wishlistItems = viewModel.wishlistItems.collectAsState()
    val total = viewModel.total.collectAsState()
    val subTotal = viewModel.subTotal.collectAsState()
    val shipping = viewModel.shipping.collectAsState()
    val tax = viewModel.tax.collectAsState()
    val currencySign = viewModel.currency.collectAsState()
    val paymentButton = remember { mutableStateOf(false) }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) })
    {

        LazyColumn(modifier = Modifier.statusBarsPadding()) {
            item {
                Row(
                    modifier = Modifier
                        .height(Dimens.appbarSize)
                        .padding(horizontal = Dimens.gird_one),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.close)
                        )
                    }

                    SpacerHorizontalOne()
                    Text(
                        text = stringResource(id = R.string.cart).uppercase(),
                        style = typography.titleLarge
                    )

                    SpacerHorizontalOne()
                    Text(
                        text = "${
                            (cartItemsUiStateState as? CartItemsUiState.Success)?.cartItems?.size ?: 0
                        } ${stringResource(id = R.string.items)}", style = typography.titleMedium
                    )
                }
                DividerHorizontal()
            }
            when (cartItemsUiStateState) {
                is CartItemsUiState.Success -> {
                    if (cartItemsUiStateState.cartItems.isNotEmpty()) {
                        paymentButton.value = true
                        items(cartItemsUiStateState.cartItems.size) {
                            CartItem(
                                cartItemsUiStateState.cartItems[it], viewModel, snackBarHostState
                            )
                        }
                    } else item { EmptyCart() }
                }
                is CartItemsUiState.Loading -> {
                    item { Loading() }
                }
                is CartItemsUiState.Error -> {

                }
            }

            item {
                val modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_two)

                SpacerVerticalOne()

                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .padding(start = 48.dp),
                    color = colorScheme.outline.copy(.3f)
                )

                SpacerVerticalFour()

                SpaceBetweenContainer(modifier = modifier) {
                    Text(
                        text = stringResource(id = R.string.total).uppercase(),
                        style = typography.titleMedium
                    )
                    Text(
                        text = "${currencySign.value} ${total.value}",
                        style = typography.headlineMedium
                    )
                }

                SpacerVerticalOne()

                SpaceBetweenContainer(
                    modifier = modifier
                ) {
                    Text(
                        text = stringResource(id = R.string.subtotal),
                        style = typography.titleMedium
                    )
                    Text(
                        text = "${currencySign.value} ${subTotal.value}",
                        style = typography.titleMedium
                    )
                }

                SpacerVerticalOne()

                SpaceBetweenContainer(
                    modifier = modifier
                ) {
                    Text(
                        text = stringResource(id = R.string.shipping),
                        style = typography.titleMedium
                    )
                    Text(
                        text = "${currencySign.value} ${shipping.value}",
                        style = typography.titleMedium
                    )
                }

                SpacerVerticalOne()

                SpaceBetweenContainer(modifier = modifier) {
                    Text(
                        text = stringResource(id = R.string.tax), style = typography.titleMedium
                    )
                    Text(
                        text = "${currencySign.value} ${tax.value}", style = typography.titleMedium
                    )
                }

                SpacerVerticalOne()

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.gird_two),
                    onClick = { /*TODO need do direct to payment gateway*/ },
                    enabled = paymentButton.value
                ) {
                    Icon(
                        Icons.Filled.CreditCard,
                        contentDescription = stringResource(R.string.complete_the_payment),
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(R.string.process_to_checkout))
                }

                DividerHorizontal()
                Row(
                    modifier = Modifier
                        .height(Dimens.appbarSize)
                        .padding(horizontal = Dimens.gird_one),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    SpacerHorizontalFour()
                    Text(
                        text = stringResource(R.string.wishlist).uppercase(),
                        style = typography.titleLarge
                    )

                    SpacerHorizontalOne()
                    Text(
                        text = "${wishlistItems.value.size} ${stringResource(id = R.string.items)}",
                        style = typography.titleMedium
                    )
                }

                DividerHorizontal()
                SpacerVerticalOne()
            }
            items(1) {
                EmptyCart()
            }
        }
    }
}

@Composable
fun CartItem(cartItems: CartItems, viewModel: CartViewModel, snackBarHostState: SnackbarHostState) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { viewModel.removeItem(cartItems, snackBarHostState) }) {
            Icon(imageVector = Icons.Default.RemoveCircleOutline, contentDescription = "")
        }
        RobinAsyncImage(
            modifier = Modifier.size(96.dp),
            contentDescription = "",
            model = cartItems.productImage + "&w=200&q=60"
        )
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
                Text(text = "LMBRJK", style = typography.bodySmall)
                Text(
                    text = "${cartItems.price?.currency} ${cartItems.price?.price}",
                    style = typography.bodyLarge
                )
            }
            Text(text = cartItems.productName ?: "", style = typography.titleMedium)
            //ExposedDropdownMenuBox()
        }
    }
}

@Composable
fun EmptyCart() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(300.dp)
                .height(100.dp),
            painter = painterResource(id = R.drawable.desert),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
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
                Icons.Filled.Store,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Explorer")

        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CartDark() {
    RobinAppPreviewScaffold {
        EmptyCart()
    }

}

@Preview
@Composable
fun CartLight() {
    RobinAppPreviewScaffold {
        // CartItem(cartItems.value[it])
    }
}
