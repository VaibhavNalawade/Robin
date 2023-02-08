package com.vaibhav.robin.presentation.ui.delivery

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.ui.common.DottedButton
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalOne
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceOrder(navController: NavController, viewModel: PlaceOrderViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    LaunchedEffect(key1 = false, block = {
        viewModel.getAddresses()
    })
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Details",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            Box {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(300.dp),
                    contentPadding = it,
                    content = {
                        when (val t = viewModel.respose) {
                            is Response.Error -> TODO()
                            Response.Loading -> {}
                            is Response.Success -> {
                                itemsIndexed(items = t.data) { i, map ->
                                    AddressItem(
                                        ind = i,
                                        map = map,
                                        selectedItem = viewModel.selectedAddressId
                                            ?: t.data[0]["Id"] as? String,
                                        onAddressSelected = {
                                            viewModel.selectedAddressId = it
                                        }
                                    )
                                }
                            }
                        }
                        item {
                            DottedButton(
                                onClick = {
                                    navController.navigate(RobinDestinations.ADDRESS_AND_PHONE)
                                },
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.add_location),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                                    Text(text = stringResource(R.string.add_address))
                                }
                            )
                        }
                    }
                )
                Button(modifier = Modifier.align(Alignment.BottomCenter),onClick = { navController.navigate(RobinDestinations.PAYMENT) }) {
                    Text(text = "Make Payment")
                }
            }
        },
    )
}

@Composable
fun AddressItem(
    ind: Int,
    map: Map<String, Any>,
    selectedItem: String?,
    onAddressSelected: (String) -> Unit
) {
    val selected = map["Id"] as? String == selectedItem
    Surface(
        modifier = Modifier
            .padding(horizontal = Dimens.gird_two, vertical = Dimens.gird_one)
            .clickable { onAddressSelected(map.get("Id") as String) },
        color = if (!selected) colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_5) else colorScheme.primary,
        shape = CardDefaults.shape,
        border = BorderStroke(1.dp, color = colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .padding(horizontal = Dimens.gird_two, vertical = Dimens.gird_one)
        ) {
            SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Address ${ind + 1}",
                    style = typography.titleMedium,
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.drive_file_rename_outline),
                        contentDescription = ""
                    )
                }
            }
            Row {
                val isHome = map.get("isHome") as Boolean
                Icon(
                    painter = if (isHome) painterResource(id = R.drawable.home_pin) else painterResource(
                        id = R.drawable.apartment
                    ),
                    contentDescription = null
                )
                SpacerHorizontalOne()
                Text(text = if (isHome) "Home" else "Workplace", style = typography.titleSmall)
            }
            Column(modifier = Modifier.padding(Dimens.gird_one)) {
                Text(
                    text = map["name"] as String,
                    style = typography.bodyLarge
                )
                Text(
                    text = map["address"] as String,
                    style = typography.bodyMedium
                )
                Text(
                    text = map["phone"] as String,
                    style = typography.bodyMedium
                )
            }
        }
    }
}