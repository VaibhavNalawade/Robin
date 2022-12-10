@file:OptIn(ExperimentalMaterial3Api::class)

package com.vaibhav.robin.presentation.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Media
import com.vaibhav.robin.data.models.Price
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Size
import com.vaibhav.robin.data.models.Variant
import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.common.*
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import java.lang.Exception


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavHostController,
    profileUiState: ProfileData?,
    toggleDrawer: () -> Unit,
    productUiState: Response<List<Product>>,
    messageBarState: MessageBarState
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            RobinAppBar(
                profileData = profileUiState,
                toggleDrawer = toggleDrawer,
                messageBarState = messageBarState
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(RobinDestinations.CART)
                },
                content = {
                    Icon(
                        painterResource(id = R.drawable.shopping_cart_fill0_wght700_grad0_opsz24),
                        contentDescription = "Localized description"
                    )
                }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
            ) {
                when (productUiState) {
                    is Response.Error -> ShowError(productUiState.message) {}
                    is Response.Loading -> Loading()
                    is Response.Success -> {
                        LazyVerticalGrid(
                            horizontalArrangement = Arrangement.spacedBy(Dimens.gird_one),
                            verticalArrangement = Arrangement.spacedBy(Dimens.gird_one),
                            contentPadding = PaddingValues(Dimens.gird_one),
                            columns = GridCells.Adaptive(minSize = 164.dp)
                        ) {
                            items(items = productUiState.data) { product ->
                                GridItem(product = product) { id ->
                                    navController.navigate(
                                        RobinDestinations.product(id).also { Log.e("nav", it) })
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobinAppBar(
    profileData: ProfileData?,
    messageBarState: MessageBarState,
    toggleDrawer: () -> Unit
) {
    Surface(
        color = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_2),
        shadowElevation = 1.dp
    ) {
        Column {
            SpacerVerticalOne()
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(horizontal = Dimens.gird_three)
                    .statusBarsPadding()
                    .height(height = 48.dp),
                tonalElevation = Dimens.surface_elevation_5,
                shadowElevation = 1.dp,
                shape = RoundedCornerShape(percent = 100)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = Dimens.gird_one)
                ) {
                    IconButton(
                        modifier = Modifier.align(alignment = Alignment.CenterStart),
                        onClick = toggleDrawer,
                        content = {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(
                                    id = R.drawable.menu_fill0_wght700_grad0_opsz24
                                ),
                                contentDescription = "",
                                tint = colorScheme.onSurfaceVariant
                            )
                        }
                    )
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.app_name),
                        style = typography.titleLarge.copy(
                            colorScheme.onSurfaceVariant
                        )
                    )
                    IconButton(modifier = Modifier.align(alignment = Alignment.CenterEnd),
                        onClick = {messageBarState.addError("Not Implemented")}
                    ) {
                        if (profileData?.image == null)
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.account_circle_fill0_wght600_grad0_opsz24
                                ),
                                contentDescription = "",
                                tint = colorScheme.onSurfaceVariant
                            )
                        else
                            CircularImage(
                                modifier = Modifier.size(size = 32.dp),
                                contentDescription = "",
                                image = profileData.image
                            )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_two),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                var filterSelected by remember {
                    mutableStateOf(false)
                }

                /**
                 * ToDo: Need to add chips when filter Selected
                 * */
                val scope = rememberCoroutineScope()
                LazyRow(
                    modifier = Modifier,
                    content = {
                        item {
                            FilterChip(
                                true,
                                onClick = {
                                },
                                label = { Text(text = "#Trending") }
                            )
                        }
                        item {
                            SpacerHorizontalOne()
                        }
                        item {
                            FilterChip(
                                true,
                                onClick = {

                                },
                                label = {
                                    Text(text = "#2022")
                                }
                            )
                        }
                    })
                SpacerHorizontalFour()
                Divider(
                    modifier = Modifier
                        .height(FilterChipDefaults.Height)
                        .width(1.dp)
                        .padding(vertical = Dimens.gird_quarter),
                    thickness = 1.dp
                )
                SpacerHorizontalOne()
                ElevatedFilterChip(selected = filterSelected,
                    onClick = {
                        filterSelected = !filterSelected

                    },
                    label = { Text(text = stringResource(R.string.filter)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.filter_alt_fill0_wght500_grad0_opsz24
                            ),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.expand_more_fill0_wght400_grad0_opsz24
                            ),
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun GridItem(
    product: Product,
    onclick: (id: String) -> Unit
) {
    Surface(
        tonalElevation = Dimens.surface_elevation_5,
        onClick = {
            onclick(product.id)
        },
        shape = MaterialTheme.shapes.small
    ) {
        Column(modifier = Modifier) {
            RobinAsyncImage(
                modifier = Modifier
                    .defaultMinSize(
                        minHeight = 220.dp,
                        minWidth = 164.dp
                    )
                    .fillMaxWidth(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                model = product.variant[0].media.images[0]
            )
            SpacerVerticalOne()
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimens.gird_two)
            ) {
                Text(
                    text = product.name,
                    style = typography.bodyLarge,
                    color = colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
                Text(
                    text = "₹ ${product.variant[0].size[0].price.retail}",
                    style = typography.bodyMedium,
                    color = colorScheme.primary,
                    maxLines = 1
                )
                SpacerVerticalOne()
            }
        }
    }
}


@Preview(group = "Grid", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GirdPreviewDark() {
    RobinAppPreview {
        GridItem(
            Product(
                name = "Lora ipsum", variant = listOf(
                    Variant(
                        media = Media(listOf("")),
                        size = listOf(Size(price = Price(retail = 1299.00)))
                    )
                )
            )
        ) {}
    }
}

@Preview(group = "Grid")
@Composable
private fun GirdPreviewLight() {
    RobinAppPreview {
        GridItem(
            Product(
                name = "Lora ipsum", variant = listOf(
                    Variant(
                        media = Media(listOf("")),
                        size = listOf(Size(price = Price(retail = 1299.00)))
                    )
                )
            )
        ) {}
    }
}
