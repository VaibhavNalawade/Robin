@file:OptIn(ExperimentalMaterial3Api::class)

package com.vaibhav.robin.presentation.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Media
import com.vaibhav.robin.data.models.Price
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Size
import com.vaibhav.robin.data.models.Variant
import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.common.*
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import kotlinx.coroutines.launch


@Composable
fun Home(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage = stringResource(id = R.string.error_occurred)
    val signInSuccessMessage = stringResource(id = R.string.signing_out_success)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    profile = viewModel.profileData,
                    userAuthenticated = viewModel.userAuthenticated,
                    navController = navController,
                    snackbarHostState = snackbarHostState
                ) {
                    if (viewModel.userAuthenticated)
                        viewModel.viewModelScope.launch {
                            viewModel.signOut().collect { response ->
                                when (response) {
                                    is Response.Error -> showMessage(
                                        state = snackbarHostState,
                                        message = errorMessage
                                    )

                                    is Response.Loading -> {
                                        /*Do Nothing*/
                                    }

                                    is Response.Success -> showMessage(
                                        state = snackbarHostState,
                                        message = signInSuccessMessage
                                    )
                                }
                            }
                        }
                    else navController.navigate(RobinDestinations.LOGIN_ROUTE)
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = {
                RobinAppBar(viewModel.profileData, drawerState, snackbarHostState)
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(RobinDestinations.CART)
                    },
                ) {
                    Icon(
                        painterResource(id = R.drawable.shopping_cart_fill0_wght700_grad0_opsz24),
                        contentDescription = "Localized description"
                    )
                }
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
            ) {
                when (val response = viewModel.products) {
                    is Response.Error -> ShowError(response.message) {}
                    is Response.Loading -> Loading()
                    is Response.Success -> {
                        LazyVerticalGrid(
                            horizontalArrangement = Arrangement.spacedBy(Dimens.gird_one),
                            verticalArrangement = Arrangement.spacedBy(Dimens.gird_one),
                            contentPadding = PaddingValues(Dimens.gird_one),
                            columns = GridCells.Adaptive(minSize = 164.dp)
                        ) {
                            items(items = response.data) { product ->
                                GridItem(product = product) { id ->
                                    navController.navigate(RobinDestinations.product(id))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobinAppBar(
    profileData: ProfileData?,
    drawerState: DrawerState,
    state: SnackbarHostState
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
                    modifier = Modifier
                        .padding(horizontal = Dimens.gird_one)
                ) {
                    val scope = rememberCoroutineScope()
                    IconButton(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart),
                        onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier,
                            painter = painterResource(
                                id = R.drawable.menu_fill0_wght700_grad0_opsz24
                            ),
                            contentDescription = "",
                            tint = colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.app_name),
                        style = typography.titleLarge.copy(
                            colorScheme.onSurfaceVariant
                        )
                    )
                    IconButton(modifier = Modifier
                        .align(alignment = Alignment.CenterEnd),
                        onClick = {
                            //TODO Profile Button Click Implementation
                            scope.launch {
                                //TODO
                                notImplemented(state)
                            }
                        }
                    ) {
                        if (profileData?.Image == null)
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
                                image = profileData.Image
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
                                    scope.launch {
                                        //TODO
                                        notImplemented(state)
                                    }
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
                                    scope.launch {
                                        //TODO
                                        notImplemented(state)
                                    }
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
                        scope.launch {
                            //TODO
                            notImplemented(state)
                        }
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
fun DrawerContent(
    profile: ProfileData?,
    userAuthenticated: Boolean,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    signOut: () -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth(.85f)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.gird_two)
        ) {
            SpacerVerticalTwo()
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage(
                    modifier = Modifier.size(64.dp),
                    contentDescription = null,
                    image = profile?.Image ?: R.drawable.profile_placeholder
                )
                SpacerHorizontalOne()
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    Arrangement.Center
                ) {
                    Text(
                        text = profile?.Name ?: "Guest User",
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight(weight = 600)
                        )
                    )
                    profile?.email?.let { email ->
                        Text(
                            text = email,
                            style = typography.bodySmall
                        )
                    }
                }
            }
        }
        SpacerVerticalTwo()
        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.home_fill0_wght700_grad0_opsz24
                    ),
                    contentDescription = null
                )
            },
            label = { Text("Home") },
            selected = true,
            onClick = {
                //TODO
                scope.launch { notImplemented(snackbarHostState) }
            },
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()
        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.shopping_bag_fill0_wght700_grad0_opsz24
                    ),
                    contentDescription = null
                )
            },
            label = { Text("Your Orders") },
            selected = false,
            onClick = {
                //TODO
                scope.launch { notImplemented(snackbarHostState) }
            },
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.shopping_cart_fill0_wght700_grad0_opsz24
                    ),
                    contentDescription = null
                )
            },
            label = { Text("Cart") },
            selected = false,
            onClick = {
                navController.navigate(RobinDestinations.CART)
            },
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()
        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.supervised_user_circle_fill0_wght700_grad0_opsz24
                    ),
                    contentDescription = null
                )
            },
            label = { Text("Profile") },
            selected = false,
            onClick = {    //TODO
                scope.launch { notImplemented(snackbarHostState) }
            },
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.settings_fill0_wght700_grad0_opsz24
                    ),
                    contentDescription = null
                )
            },
            label = { Text("Settings") },
            selected = false,
            onClick = {    //TODO
                scope.launch { notImplemented(snackbarHostState) }
            },
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()
        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.support_fill0_wght700_grad0_opsz24
                    ),
                    contentDescription = null
                )
            },
            label = { Text("Help") },
            selected = false,
            onClick = {
                //TODO
                scope.launch { notImplemented(snackbarHostState) }
            },
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()
        AuthUserNavigationItem(
            userAuthenticated,
            signOut = signOut
        )
    }
}

@Composable
fun AuthUserNavigationItem(
    userAuthenticated: Boolean,
    signOut: () -> Unit
) {
    val user = if (userAuthenticated) stringResource(id = R.string.sign_out)
    else stringResource(id = R.string.sign_in)
    NavigationDrawerItem(
        modifier = Modifier
            .padding(NavigationDrawerItemDefaults.ItemPadding),
        icon = {
            Icon(
                painterResource(
                    id = R.drawable.logout_fill0_wght700_grad0_opsz24
                ),
                contentDescription = null
            )
        },
        label = { Text(user) },
        selected = false,
        onClick = { signOut() },
    )
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

suspend fun notImplemented(state: SnackbarHostState) {
    state.showSnackbar("This function not constructed \uD83C\uDFD7️")
}

suspend fun showMessage(state: SnackbarHostState, message: String) {
    state.showSnackbar(message)
}


@Preview(group = "Grid", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GirdPreviewDark() {
    RobinAppPreviewScaffold {
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
    RobinAppPreviewScaffold {
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
