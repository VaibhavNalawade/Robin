@file:OptIn(ExperimentalMaterial3Api::class)

package com.vaibhav.robin.presentation.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Media
import com.vaibhav.robin.data.models.Price
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Size
import com.vaibhav.robin.data.models.Variant
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.ui.common.*
import com.vaibhav.robin.presentation.ui.theme.Values
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavHostController, snackbarHostState: SnackbarHostState, viewModel: HomeViewModel
) {
    val topAppBarScrollState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarScrollState)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(viewModel, navController)
            }
        }) {
        RobinBar(modifier = Modifier.statusBarsPadding(),
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch(Dispatchers.Main) {
                        when (drawerState.isClosed) {
                            true -> drawerState.open()
                            false -> drawerState.close()
                        }
                    }
                }) {
                    Icon(
                        painterResource(id = R.drawable.menu_fill0_wght700_grad0_opsz24),
                        contentDescription = "Localized description"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    navController.navigate(
                        RobinDestinations.searchQuery(
                            "null"
                        )
                    )
                }) {
                    Icon(
                        painterResource(id = R.drawable.search_fill0_wght700_grad0_opsz24),
                        contentDescription = "Localized description"
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            fab = {
                FloatingActionButton(
                    onClick = { navController.navigate(RobinDestinations.CART) },
                ) {
                    Icon(
                        painterResource(id = R.drawable.shopping_cart_fill0_wght700_grad0_opsz24),
                        contentDescription = "Localized description"
                    )
                }
            }) {
            Surface(modifier = Modifier.fillMaxSize(),tonalElevation = Values.Dimens.surface_elevation_1) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .height(96.dp)
                            .fillMaxWidth()
                    )
                    Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Values.Dimens.gird_two),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            var filterSelected by remember {
                                mutableStateOf(false)
                            }
                            Divider(
                                modifier = Modifier
                                    .height(38.dp)
                                    .width(2.dp), thickness = 1.dp
                            )
                            SpacerHorizontalOne()
                            ElevatedFilterChip(
                                selected = filterSelected,
                                onClick = { /*TODO*/ },
                                label = { Text(text = stringResource(R.string.filter)) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.filter_alt_fill0_wght500_grad0_opsz24),
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.expand_more_fill0_wght400_grad0_opsz24),
                                        contentDescription = null
                                    )
                                }


                            )
                        }
                    }
                    when (val res = viewModel.products) {
                        is Response.Error -> ShowError(res.message) {}
                        Response.Loading -> Loading()
                        is Response.Success -> LazyVerticalGrid(
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp
                            ),
                            contentPadding = PaddingValues(Values.Dimens.gird_one),
                            columns = GridCells.Adaptive(164.dp)
                        ) {

                            items(items = res.data) {
                                GridItem(product = it) {
                                    navController.navigate(RobinDestinations.product(it))
                                }
                            }

                        }
                    }

                }
            }
        }
    }
}

@Composable
fun DrawerContent(viewModel: HomeViewModel, navController: NavHostController) {

    val profile = viewModel.profileData

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth(.85f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            SpacerVerticalTwo()

            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularImage(
                    modifier = Modifier.size(64.dp),
                    contentDescription = null,
                    image = profile?.Image ?: R.drawable.profile_placeholder
                )

                SpacerHorizontalOne()

                Column(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {

                    Text(
                        text = profile?.Name ?: "Guest User",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight(
                                600
                            )
                        )
                    )

                    profile?.email?.let {

                        Text(
                            text = it, style = MaterialTheme.typography.bodySmall
                        )
                    }

                }
            }
        }
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.home_fill0_wght700_grad0_opsz24),
                    contentDescription = null
                )
            },
            label = { Text("Home") },
            selected = true,
            onClick = {

            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.shopping_bag_fill0_wght700_grad0_opsz24),
                    contentDescription = null
                )
            },
            label = { Text("Your Orders") },
            selected = false,
            onClick = {},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.shopping_cart_fill0_wght700_grad0_opsz24),
                    contentDescription = null
                )
            },
            label = { Text("Cart") },
            selected = false,
            onClick = {},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.supervised_user_circle_fill0_wght700_grad0_opsz24),
                    contentDescription = null
                )
            },
            label = { Text("Profile") },
            selected = false,
            onClick = {},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.settings_fill0_wght700_grad0_opsz24),
                    contentDescription = null
                )
            },
            label = { Text("Settings") },
            selected = false,
            onClick = {},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.support_fill0_wght700_grad0_opsz24),
                    contentDescription = null
                )
            },
            label = { Text("Help") },
            selected = false,
            onClick = {},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        SpacerVerticalTwo()
        AuthUserNavigationItem(viewModel = viewModel, navController = navController)
    }
}

@Composable
fun AuthUserNavigationItem(viewModel: HomeViewModel, navController: NavHostController) {
    val user = if (viewModel.userAuthenticated) "Sign out"
    else "Sign in"
    val localContext = LocalContext.current

    NavigationDrawerItem(
        icon = {
            Icon(
                painterResource(id = R.drawable.logout_fill0_wght700_grad0_opsz24),
                contentDescription = null
            )
        },
        label = { Text(user) },
        selected = false,
        onClick = {
            if (viewModel.userAuthenticated) viewModel.viewModelScope.launch {
                viewModel.signOut().collect { response ->
                    when (response) {
                        is Response.Error -> Toast.makeText(
                            localContext, "Unable to SignOut", Toast.LENGTH_LONG
                        ).show()

                        is Response.Loading -> Toast.makeText(
                            localContext, "Loading", Toast.LENGTH_SHORT
                        ).show()

                        is Response.Success -> Toast.makeText(
                            localContext, "Sign-out Success", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            else navController.navigate(RobinDestinations.LOGIN_ROUTE)
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@Composable
fun GridItem(product: Product,onclick:(id:String)->Unit) {
    Surface(
        tonalElevation = Values.Dimens.surface_elevation_5,
        onClick = {onclick(product.id)},
        shape = MaterialTheme.shapes.small
    ) {
        Column(modifier = Modifier) {
            RobinAsyncImage(
                modifier = Modifier.defaultMinSize(minHeight = 220.dp, minWidth = 164.dp),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                model = product.variant[0].media.images[0]
            )
            SpacerVerticalOne()

            Column(modifier = Modifier.padding(horizontal = Values.Dimens.gird_two)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                SpacerVerticalOne()

                Text(
                    text = "₹ ${product.variant[0].size[0].price.retail}",
                    style = MaterialTheme.typography.bodyMedium
                )
                SpacerVerticalOne()
            }
        }
    }
}

@Preview(group = "Grid", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GirdPreviewDark() {
    RobinAppPreviewScaffold {
        GridItem(
            Product(
                name = "Loram ipsum",
                variant = listOf(Variant(media = Media(listOf("")),size = listOf(Size(price = Price(retail = 1299.00)))))
            )
        ){}
    }
}

@Preview(group = "Grid")
@Composable
private fun GirdPreviewLight() {
    RobinAppPreviewScaffold {
        GridItem(
            Product(
                name = "Loram ipsum",
                variant = listOf(Variant(media = Media(listOf("")),size = listOf(Size(price = Price(retail = 1299.00)))))
            )
        ){}
    }
}

@Preview
@Composable
fun DrawerContentPreviewLight() {
    RobinAppPreviewScaffold {
        val ctx = LocalContext.current
        DrawerContent(viewModel(), NavHostController(ctx))
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DrawerContentPreviewDark() {
    RobinAppPreviewScaffold {
        // DrawerContent()
    }
}

