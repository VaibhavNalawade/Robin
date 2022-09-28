package com.vaibhav.robin.presentation.ui.cart


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.common.*
import com.vaibhav.robin.presentation.theme.Values.Dimens


@Composable
fun Cart(
    viewModel: CartViewModel,
    navController: NavController,
    snackBarHostState: SnackbarHostState
) {
    val paymentButton = remember { mutableStateOf(false) }
    Surface(tonalElevation = Dimens.surface_elevation_1) {


        LazyColumn(modifier = Modifier.statusBarsPadding()) {
            item {
                CartAppBar {
                    navController.popBackStack()
                }
                DividerHorizontal()
            }

        }
    }
}

@Composable
fun CartAppBar(onBackButton: () -> Unit) {
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
                text = stringResource(id = R.string.cart).uppercase(),
                style = typography.titleLarge
            )

            SpacerHorizontalOne()
            Text(
                text = "0 Items", style = typography.titleMedium
            )
        }
    }
}

@Composable
fun CartItem( ) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.circle_remove_fill0_wght700_grad0_opsz24),
                contentDescription = null
            )
        }
        RobinAsyncImage(
            modifier = Modifier.size(96.dp),
            contentDescription = "",
            model = ""
        )
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
                Text(text = "LMBRJK", style = typography.bodySmall)
                Text(
                    text = "",
                    style = typography.bodyLarge
                )
            }
            Text(text = "", style = typography.titleMedium)
            //ExposedDropdownMenuBox()
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
            modifier = Modifier
                .fillMaxWidth(),
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


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CartDark() {
    RobinAppPreviewScaffold {
        CartAppBar {

        }
        EmptyCart()
    }

}

@Preview
@Composable
fun CartLight() {
    RobinAppPreviewScaffold {
        CartAppBar {

        }
        EmptyCart()
    }
}
