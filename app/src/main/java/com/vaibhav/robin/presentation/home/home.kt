 @file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.vaibhav.robin.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vaibhav.robin.navigation.RobinDestinations
import com.vaibhav.robin.presentation.common.CustomListHorizontal
import com.vaibhav.robin.presentation.common.RobinBar
import com.vaibhav.robin.presentation.common.RowItem
import com.vaibhav.robin.presentation.common.TrendingChipRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

 @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, snackbarHostState: SnackbarHostState) {
val topAppBarScrollState= rememberTopAppBarState()
    val viewModel: HomeViewModel = viewModel()
    val scrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarScrollState) }
    val scope = rememberCoroutineScope()

    RobinBar(
        modifier = Modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = {
              notImplemented(scope, snackbarHostState =snackbarHostState)
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = {navController.navigate(RobinDestinations.searchQuery("null"))}) {
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
        }
    ) {
            LazyColumn {
                item {
                    Spacer(
                        modifier = Modifier
                            .height(96.dp)
                            .fillMaxWidth()
                ) }
                item {  TrendingChipRow(viewModel.trendingItemsState.collectAsState().value,navController) }
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
                            RowItem(unsplashGet = it){

                                   navController.navigate(RobinDestinations.product("zJopv4mGi0Sj2ttxsdQh"))
                               }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }
    }

fun notImplemented(scope: CoroutineScope, snackbarHostState: SnackbarHostState)=
    scope.launch { snackbarHostState.showSnackbar("\uD83E\uDD7A Not" +
            " Implemented")
}