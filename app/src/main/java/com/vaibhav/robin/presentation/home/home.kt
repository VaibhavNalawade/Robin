@file:Suppress("OPT_IN_IS_NOT_ENABLED")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.vaibhav.robin.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.common.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Home(
    navController: NavHostController, snackbarHostState: SnackbarHostState, viewModel: HomeViewModel
) {
    val topAppBarScrollState = rememberTopAppBarState()
    val scrollBehavior =
        remember { TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarScrollState) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val selectedItem = remember { mutableStateOf(items[0]) }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            DrawerContent(viewModel, navController)
        }
    }, content = {

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
                        imageVector = Icons.Filled.Menu,
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
                        imageVector = Icons.Filled.Search,
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
                        Icons.Filled.ShoppingCart,
                        contentDescription = "Localized description"
                    )
                }
            }) {
            LazyColumn {
                item {
                    Spacer(
                        modifier = Modifier
                            .height(96.dp)
                            .fillMaxWidth()
                    )
                }
                item {
                    TrendingChipRow(
                        viewModel.trendingItemsState.collectAsState().value, navController
                    )
                }
                /*item {
                    Box(contentAlignment = Alignment.BottomCenter) {
                        ImageSlider(viewModel.bannerImageData){
                            notImplemented(scope, snackbarHostState)
                        }
                    }
                }*/
                item {
                    CustomListHorizontal(
                        listResults = viewModel.trendingProducts.value,
                        text = "Trending",
                    )
                }
                item {
                    Column(
                        Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(vertical = 8.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Deals of Day",
                                style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.surface),
                            )
                            TextButton(
                                modifier = Modifier,
                                onClick = { /* Do something! */ }) { Text("SEE ALL") }
                        }
                        if (viewModel.tradingProductRequest.value.isEmpty()) {
                            RowItem(placeholder = true)
                            Spacer(modifier = Modifier.height(4.dp))
                            RowItem(placeholder = true)
                            Spacer(modifier = Modifier.height(4.dp))
                            RowItem(placeholder = true)
                        }
                        viewModel.tradingProductRequest.value.forEach {
                            RowItem(unsplashGet = it) {

                                navController.navigate(RobinDestinations.product("zJopv4mGi0Sj2ttxsdQh"))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }
    })
}


@Composable
fun DrawerContent(viewModel: HomeViewModel, navController: NavHostController) {

    val localContext = LocalContext.current
    val profile = viewModel.profileData

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth(.85f)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {

            SpacerVerticalTwo()

            Row(verticalAlignment = Alignment.CenterVertically) {

                var painter = painterResource(id = R.drawable.profile_placeholder)
                val asyncImagePainter = rememberAsyncImagePainter(model = profile?.Image ?: "")

                if (asyncImagePainter.state is AsyncImagePainter.State.Success) {
                    painter = asyncImagePainter
                }

                CircularImage(
                    modifier = Modifier.size(64.dp),
                    contentDescription = "",
                    Image = painter,
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
                            text = it,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                }
            }
        }
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = true,
            onClick = {

            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = { Icon(Icons.Outlined.ShoppingBag, contentDescription = null) },
            label = { Text("Your Orders") },
            selected = false,
            onClick = {
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = null) },
            label = { Text("Cart") },
            selected = false,
            onClick = {
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.SupervisedUserCircle, contentDescription = null) },
            label = { Text("Profile") },
            selected = false,
            onClick = {
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            label = { Text("Settings") },
            selected = false,
            onClick = {
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(
            icon = { Icon(Icons.Outlined.Support, contentDescription = null) },
            label = { Text("Help") },
            selected = false,
            onClick = {
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        SpacerVerticalTwo()

        val user = if (viewModel.userAuthenticated)
            "Sign out"
        else "Sign in"

        NavigationDrawerItem(
            icon = { Icon(Icons.Outlined.Logout, contentDescription = null) },
            label = { Text(user) },
            selected = false,
            onClick = {
                if (viewModel.userAuthenticated)

                    viewModel.viewModelScope.launch {
                        viewModel.signOut().collect { response ->
                            when (response) {
                                is Response.Error -> Toast.makeText(
                                    localContext,
                                    "Unable to SignOut",
                                    Toast.LENGTH_LONG
                                ).show()
                                is Response.Loading -> Toast.makeText(
                                    localContext,
                                    "Loading",
                                    Toast.LENGTH_SHORT
                                ).show()
                                is Response.Success -> Toast.makeText(
                                    localContext,
                                    "Sign-out Success",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                else navController.navigate(RobinDestinations.LOGIN_ROUTE)
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
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

