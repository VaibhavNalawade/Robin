@file:Suppress("OPT_IN_IS_NOT_ENABLED") @file:OptIn(ExperimentalMaterial3Api::class)

package com.vaibhav.robin.presentation.ui.home

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
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.common.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Home(
    navController: NavHostController, snackbarHostState: SnackbarHostState, viewModel: HomeViewModel
) {
    val topAppBarScrollState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarScrollState)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val selectedItem = remember { mutableStateOf(items[0]) }

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
                        Icons.Filled.ShoppingCart, contentDescription = "Localized description"
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
                    Button(onClick = { navController.navigate(RobinDestinations.product("D3MjFbzN2Grpl0QRU9cv")) }) {

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

        NavigationDrawerItem(icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = true,
            onClick = {

            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(icon = { Icon(Icons.Outlined.ShoppingBag, contentDescription = null) },
            label = { Text("Your Orders") },
            selected = false,
            onClick = {},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(icon = {
            Icon(
                Icons.Outlined.ShoppingCart, contentDescription = null
            )
        },
            label = { Text("Cart") },
            selected = false,
            onClick = {},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(icon = {
            Icon(
                Icons.Default.SupervisedUserCircle, contentDescription = null
            )
        },
            label = { Text("Profile") },
            selected = false,
            onClick = {},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            label = { Text("Settings") },
            selected = false,
            onClick = {},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        SpacerVerticalTwo()

        NavigationDrawerItem(icon = { Icon(Icons.Outlined.Support, contentDescription = null) },
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

    NavigationDrawerItem(icon = { Icon(Icons.Outlined.Logout, contentDescription = null) },
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

