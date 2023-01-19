package com.vaibhav.robin.presentation.ui.common

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.flowlayout.FlowRow
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.MainBrand
import com.vaibhav.robin.data.models.MainCategory
import com.vaibhav.robin.data.models.QueryProduct
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.Order
import com.vaibhav.robin.presentation.RobinNavigationType
import com.vaibhav.robin.presentation.models.state.FilterState
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import com.vaibhav.robin.presentation.ui.theme.Values.Shapes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    userAuthenticated: Boolean,
    navController: NavHostController,
    signOut: () -> Unit,
    closeDrawer: () -> Unit,
    brandsUiState: Response<List<MainBrand>>,
    categoriesUiState: Response<List<MainCategory>>,
    showNavContent: MutableState<Boolean>,
    onApply: (QueryProduct) -> Unit,
    navigationType: RobinNavigationType,
    filterState: FilterState
) {
    if (navigationType == RobinNavigationType.PERMANENT_NAVIGATION_DRAWER)
        PermanentDrawerSheet(
            drawerTonalElevation = Dimens.surface_elevation_1
        ) {
            DrawerContent(
                userAuthenticated = userAuthenticated,
                navController = navController,
                signOut = signOut,
                closeDrawer = closeDrawer,
                brandsUiState = brandsUiState,
                categoriesUiState = categoriesUiState,
                showNavContent = showNavContent,
                onApply = onApply,
                navigationType = navigationType,
                filterState = filterState
            )
        }
    else ModalDrawerSheet {
        DrawerContent(
            userAuthenticated = userAuthenticated,
            navController = navController,
            signOut = signOut,
            closeDrawer = closeDrawer,
            brandsUiState = brandsUiState,
            categoriesUiState = categoriesUiState,
            showNavContent = showNavContent,
            onApply = onApply,
            navigationType = navigationType,
            filterState = filterState
        )
    }
}

@Composable
fun DrawerContent(
    userAuthenticated: Boolean,
    navController: NavHostController,
    signOut: () -> Unit,
    closeDrawer: () -> Unit,
    brandsUiState: Response<List<MainBrand>>,
    categoriesUiState: Response<List<MainCategory>>,
    showNavContent: MutableState<Boolean>,
    onApply: (QueryProduct) -> Unit,
    navigationType: RobinNavigationType,
    filterState: FilterState
) {
    Column(
        modifier = Modifier
    ) {
        SpacerVerticalOne()
        SpaceBetweenContainer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.gird_three),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = colorScheme.primary,
                style = typography.titleLarge
            )
            AnimatedVisibility(
                visible = navigationType != RobinNavigationType.PERMANENT_NAVIGATION_DRAWER
            ) {
                IconButton(onClick = closeDrawer) {
                    Icon(
                        painter = painterResource(id = R.drawable.menu_open),
                        contentDescription = "",
                        tint = colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        SpacerVerticalOne()
        DividerHorizontal()
        SpacerVerticalOne()
        if (navigationType == RobinNavigationType.PERMANENT_NAVIGATION_DRAWER)
            AnimatedContent(
                showNavContent = showNavContent,
                navController = navController,
                closeDrawer = closeDrawer,
                userAuthenticated = userAuthenticated,
                signOut = signOut,
                brandsUiState = brandsUiState,
                categoriesUiState = categoriesUiState,
                onApply = onApply,
                filterState = filterState
            )
        else NonAnimatedContent(
            showNavContent = showNavContent,
            navController = navController,
            closeDrawer = closeDrawer,
            userAuthenticated = userAuthenticated,
            signOut = signOut,
            brandsUiState = brandsUiState,
            categoriesUiState = categoriesUiState,
            onApply = onApply,
            filterState = filterState
        )
    }
}

@Composable
private fun NonAnimatedContent(
    showNavContent: MutableState<Boolean>,
    navController: NavHostController,
    closeDrawer: () -> Unit,
    userAuthenticated: Boolean,
    signOut: () -> Unit,
    brandsUiState: Response<List<MainBrand>>,
    categoriesUiState: Response<List<MainCategory>>,
    onApply: (QueryProduct) -> Unit,
    filterState: FilterState
) {
    if (!showNavContent.value)
        NavigationItems(
            navController = navController,
            closeDrawer = closeDrawer,
            userAuthenticated = userAuthenticated,
            signOut = signOut
        )
    else if (navController.currentBackStackEntryAsState().value?.destination?.route == RobinDestinations.HOME)
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.gird_two)
                .verticalScroll(state = rememberScrollState())
        ) {
            SortUi(
                showNavContent = showNavContent,
                brandsUiState = brandsUiState,
                categoriesUiState = categoriesUiState,
                closeDrawer = closeDrawer,
                onApply = onApply,
                filterState = filterState
            )
        }
}

@Composable
private fun AnimatedContent(
    showNavContent: MutableState<Boolean>,
    navController: NavHostController,
    closeDrawer: () -> Unit,
    userAuthenticated: Boolean,
    signOut: () -> Unit,
    brandsUiState: Response<List<MainBrand>>,
    categoriesUiState: Response<List<MainCategory>>,
    onApply: (QueryProduct) -> Unit,
    filterState: FilterState
) {
    if (!showNavContent.value) {
        var visablity by remember { mutableStateOf(false) }
        LaunchedEffect(
            key1 = true,
            block = {
                visablity = true
            })
        SlideInLeftVisibilityAnimation(visible = visablity) {
            NavigationItems(
                navController = navController,
                closeDrawer = closeDrawer,
                userAuthenticated = userAuthenticated,
                signOut = signOut
            )
        }
    } else if (navController.currentBackStackEntryAsState().value?.destination?.route == RobinDestinations.HOME) {
        var visablity by remember { mutableStateOf(false) }
        LaunchedEffect(
            key1 = true,
            block = {
                visablity = true
            })
        SlideInRightVisibilityAnimation(visible = visablity) {
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimens.gird_two)
                    .verticalScroll(state = rememberScrollState())
            ) {
                SortUi(
                    categoriesUiState = categoriesUiState,
                    brandsUiState = brandsUiState,
                    showNavContent = showNavContent,
                    closeDrawer = closeDrawer,
                    onApply = onApply,
                    filterState = filterState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItems(
    navController: NavHostController,
    closeDrawer: () -> Unit,
    userAuthenticated: Boolean,
    signOut: () -> Unit
) {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route
    val ctx = LocalContext.current
    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
    ) {
        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            selected = route == RobinDestinations.HOME,
            icon = {
                Icon(
                    painterResource(id = R.drawable.home),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.home),
                    style = typography.labelLarge
                )
            },
            onClick = {
                if (route != RobinDestinations.HOME)
                    navController.navigate(RobinDestinations.HOME) {
                        popUpTo(RobinDestinations.HOME) {
                            inclusive = true
                        }
                    }
                closeDrawer()
            }
        )

        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            selected = false,
            onClick = closeDrawer,
            icon = {
                Icon(
                    painterResource(id = R.drawable.shopping_bag),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.your_orders),
                    style = typography.labelLarge
                )
            }
        )

        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            selected = route == RobinDestinations.CART,
            icon = {
                Icon(
                    painterResource(id = R.drawable.shopping_cart),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    stringResource(id = R.string.cart),
                    style = typography.labelLarge
                )
            },
            onClick = {
                if (route != RobinDestinations.CART) navController.navigate(RobinDestinations.CART)
                closeDrawer()
            }
        )

        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            selected = false,
            onClick = closeDrawer,
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.account_circle
                    ), contentDescription = null
                )
            },
            label = {
                Text(
                    stringResource(id = R.string.profile),
                    style = typography.labelLarge
                )
            }
        )

        AuthUserNavigationItem(
            userAuthenticated = userAuthenticated,
            signOut = signOut,
            selected = route == RobinDestinations.LOGIN,
            signIn = {
                if (route != RobinDestinations.LOGIN) {
                    navController.navigate(RobinDestinations.LOGIN)
                    closeDrawer()
                }
            }
        )

        DividerHorizontal()

        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            icon = {
                Icon(
                    painterResource(id = R.drawable.settings),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.settings),
                    style = typography.labelLarge
                )
            },
            selected = false,
            onClick = closeDrawer,
        )

        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            icon = {
                Icon(
                    painterResource(id = R.drawable.support),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    stringResource(R.string.help),
                    style = typography.labelLarge
                )
            },
            selected = false,
            onClick = closeDrawer,
        )
        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            icon = {
                Icon(
                    painterResource(id = R.drawable.cancel),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.exit),
                    style = typography.labelLarge
                )
            },
            selected = false,
            onClick = {
                (ctx as Activity).finishAndRemoveTask()
            },
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthUserNavigationItem(
    userAuthenticated: Boolean,
    signOut: () -> Unit,
    selected: Boolean,
    signIn: () -> Unit,
) {
    val userAction: String
    val icon: Painter
    val onclick: () -> Unit
    if (userAuthenticated) {
        userAction = stringResource(id = R.string.sign_out)
        icon = painterResource(id = R.drawable.signout)
        onclick = signOut
    } else {
        userAction = stringResource(id = R.string.sign_in)
        icon = painterResource(id = R.drawable.signin)
        onclick = signIn
    }
    NavigationDrawerItem(
        shape = Shapes.NavigationItemShape,
        modifier = Modifier.padding(end = Dimens.gird_two),
        icon = {
            Icon(
                painter = icon, contentDescription = null
            )
        },
        label = {
            Text(
                text = userAction,
                style = typography.labelLarge
            )
        },
        selected = selected,
        onClick = onclick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortUi(
    categoriesUiState: Response<List<MainCategory>>,
    brandsUiState: Response<List<MainBrand>>,
    showNavContent: MutableState<Boolean>,
    closeDrawer: () -> Unit,
    onApply: (QueryProduct) -> Unit,
    filterState: FilterState
) {
    var brandSelectedIndex by filterState.brandIndex
    var categorySelectedIndex by filterState.categoryIndex
    var order by filterState.sortOrder

    SpacerVerticalOne()
    Text(
        text = stringResource(id = R.string.filter),
        style = typography.titleMedium
    )
    when (brandsUiState) {
        is Response.Error -> {}
        Response.Loading -> {}
        is Response.Success -> {
            SpacerVerticalOne()
            Text(
                text = stringResource(R.string.brands),
                style = typography.titleSmall
            )
            SpacerVerticalOne()
            FlowRow(mainAxisSpacing = Dimens.gird_one) {
                brandsUiState.data.forEachIndexed { i, state ->
                    FilterChip(
                        selected = brandSelectedIndex == i,
                        onClick = {
                            brandSelectedIndex =
                                if (brandSelectedIndex != i) i
                                else null
                        },
                        label = {
                            Text(text = state.name)
                        },
                        leadingIcon = {
                            RobinAsyncImage(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape),
                                model = state.logo,
                                contentDescription = ""
                            )
                        }
                    )
                }
            }
        }
    }
    when (categoriesUiState) {
        is Response.Error -> {}
        Response.Loading -> {}
        is Response.Success -> {
            SpacerVerticalOne()
            Text(text = stringResource(R.string.category), style = typography.titleSmall)
            SpacerVerticalOne()
            FlowRow(mainAxisSpacing = Dimens.gird_one) {
                categoriesUiState.data.forEachIndexed { index, filterChipState ->
                    FilterChip(
                        selected = categorySelectedIndex == index,
                        onClick = {
                            categorySelectedIndex =
                                if (categorySelectedIndex != index) index
                                else null
                        },
                        label = {
                            Text(text = filterChipState.name)
                        }
                    )
                }
            }
            SpacerVerticalOne()
        }
    }
    Text(text = stringResource(R.string.sort_by), style = typography.titleSmall)
    SpacerVerticalTwo()
    Row {
        ToggleFilterButton(
            checked = order == Order.ASCENDING,
            changed = { order = Order.ASCENDING },
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.sort),
                    contentDescription = ""
                )
            }
        )
        ToggleFilterButton(
            checked = order == Order.DESCENDING,
            changed = { order = Order.DESCENDING },
            content = {
                Icon(
                    modifier = Modifier.rotate(180F),
                    painter = painterResource(id = R.drawable.sort),
                    contentDescription = ""
                )
            }
        )
    }
    SpacerVerticalTwo()
    SpaceBetweenContainer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.gird_two)
    ) {
        FilledTonalButton(
            onClick = {
                onApply(QueryProduct())
                showNavContent.value = false
                closeDrawer()
                brandSelectedIndex = null
                categorySelectedIndex = null
                order = null
            },
            content = {
                Text(text = stringResource(id = R.string.cancel))
            }
        )
        Button(
            onClick = {
                onApply(
                    QueryProduct(
                        brandId = brandSelectedIndex?.let {
                            (brandsUiState as? Response.Success)?.data?.get(it)?.id
                        },
                        categoryId = categorySelectedIndex?.let {
                            (categoriesUiState as? Response.Success)?.data?.get(it)?.id
                        },
                        order = order
                    )
                )
                showNavContent.value = false
                closeDrawer()
            },
            content = {
                Text(text = stringResource(id = R.string.apply))
            }
        )
    }
}
