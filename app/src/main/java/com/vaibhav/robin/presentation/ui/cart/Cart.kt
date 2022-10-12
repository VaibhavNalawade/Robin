package com.vaibhav.robin.presentation.ui.cart


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.models.common.DropdownOption
import com.vaibhav.robin.presentation.models.state.DropdownState
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage
import com.vaibhav.robin.presentation.ui.common.RobinDropdownMenuBox
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalOne
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalTwo
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens


@Composable
fun Cart(
    viewModel: CartViewModel,
    navController: NavController,
    snackBarHostState: SnackbarHostState
) {
    LaunchedEffect(key1 = false, block = {
        viewModel.launch()
    })
    Surface(tonalElevation = Dimens.surface_elevation_1) {
        Box {
            Column(modifier = Modifier.statusBarsPadding()) {
                CartAppBar(items = 0) { navController.popBackStack() }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {

                    when (val item = viewModel.cartItem) {
                        is Response.Error -> TODO()
                        Response.Loading -> item { Loading() }
                        is Response.Success -> item.data.forEachIndexed { index, cartItem ->
                            item {
                                CartItemListLoader(
                                    cartItemIndex = index, productList = viewModel.productData
                                ) {
                                    viewModel.getDetails(cartItem.productId, index)
                                }
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Button(modifier = Modifier.fillMaxWidth(.8f), onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.shopping_cart_checkout_fill0_wght700_grad200_opsz24), contentDescription ="" )
                    SpacerHorizontalOne()
                    Text(text = "Checkout")
                }
            }
        }
    }
}

@Composable
fun CartItemListLoader(
    cartItemIndex: Int,
    productList: SnapshotStateList<Response<Product>>,
    function: suspend () -> Unit
) {
    LaunchedEffect(key1 = true, block = { function.invoke() })
    if (productList.isNotEmpty()) when (val product = productList[cartItemIndex]) {
        Response.Loading -> {
            CartItemLoading()
        }

        is Response.Error -> TODO()
        is Response.Success -> CartItem(product = product.data, variantIndex = 0)
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
                    painter = painterResource(id = R.drawable.close_fill0_wght700_grad0_opsz24),
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartItem(product: Product, variantIndex: Int) {
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
                model = product.variant[variantIndex].media.images[0]
            )
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = product.name, style = typography.titleMedium
                )
                SpacerVerticalOne()
                Surface(
                    tonalElevation = Dimens.surface_elevation_1, shape = MaterialTheme.shapes.medium
                ) {
                    Row {
                        RobinAsyncImage(
                            modifier = Modifier.size(24.dp),
                            contentDescription = "",
                            model = product.brand.url
                        )
                        SpacerHorizontalOne()
                        Text(text = product.brand.name, style = typography.bodyMedium)
                        SpacerHorizontalTwo()
                        Text(
                            text = "$ ${product.variant[variantIndex].size[0].price.retail.toString()}",
                            style = typography.titleMedium
                        )
                        SpacerHorizontalOne()
                    }
                }
                SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
                 Row(verticalAlignment = Alignment.CenterVertically){
                     IconButton(onClick = { /*TODO*/ }) {
                         Icon(
                             painter = painterResource(id = R.drawable.add_circle_fill0_wght200_grad200_opsz20),
                             contentDescription = ""
                         )
                     }
                     Text(text = "1", style = typography.labelLarge)
                     IconButton(onClick = { /*TODO*/ }) {
                         Icon(
                             painter = painterResource(id = R.drawable.do_not_disturb_on_fill0_wght200_grad200_opsz20),
                             contentDescription = ""
                         )
                     }
                 }
                    FilledTonalIconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete_fill0_wght700_grad200_opsz24),
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
            painter = painterResource(id = R.drawable.desert),
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
                Icons.Filled.Store,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Explorer")

        }
    }
}

