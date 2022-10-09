package com.vaibhav.robin.presentation.ui.cart


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.ui.common.DividerHorizontal
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalOne
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens


@Composable
fun Cart(
    viewModel: CartViewModel, navController: NavController, snackBarHostState: SnackbarHostState
) {
    Surface(tonalElevation = Dimens.surface_elevation_1) {
        LazyColumn(modifier = Modifier.statusBarsPadding()) {
            item {
                CartAppBar(items = 0) { navController.popBackStack() }
            }
            item {
                ShowCartList(product = "", variantIndex =0 , viewModel =viewModel )
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

@Composable
fun ShowCartList(product: String, variantIndex: Int, viewModel: CartViewModel) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black))
    viewModel.productData.add(0, Response.Loading)
    LaunchedEffect(key1 = 0, block = { viewModel.fetchproduct(product, 0) })
    val d = viewModel.productData[0]
    when (d) {
        is Response.Error -> TODO()
        Response.Loading -> {

        }

        is Response.Success -> {

            CartItem(d.data, variantIndex)
      }  }

}

@Composable
fun CartItem(product:Product, variantIndex: Int) {

    Row(
        modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        RobinAsyncImage(
            modifier = Modifier.size(96.dp),
            contentDescription = "",
            model = product.variant[variantIndex].media.images[0]
        )
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
                Text(text = product.brand.name, style = typography.bodySmall)
                Text(
                    text = product.name, style = typography.bodyLarge
                )
            }
            Text(
                text = product.variant[variantIndex].size[0].price.retail.toString(),
                style = typography.titleMedium
            )
        }
    }
}

@Composable
fun CartItemLoading() {

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

