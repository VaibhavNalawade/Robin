package com.vaibhav.robin.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.MainBrand
import com.vaibhav.robin.data.models.MainCategory
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.QueryProduct
import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.models.state.FilterState
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.ui.navigation.RobinNavHost
import com.vaibhav.robin.presentation.ui.common.ContentWithMessageBar
import com.vaibhav.robin.presentation.ui.common.NavigationDrawer
import com.vaibhav.robin.presentation.ui.common.MessageBarPosition
import com.vaibhav.robin.presentation.ui.common.NavigationRailsContent
import com.vaibhav.robin.presentation.ui.common.rememberMessageBarState
import com.vaibhav.robin.presentation.ui.theme.RobinTheme
import kotlinx.coroutines.launch

@Composable
fun RobinApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    signOut: () -> Unit,
    userAuthenticated: Boolean,
    profileUiState: ProfileData?,
    productUiState: Response<List<Product>>,
    categoriesUiState: Response<List<MainCategory>>,
    brandsUiState: Response<List<MainBrand>>,
    onApply: (QueryProduct) -> Unit,
    filterState: FilterState,
    selectedProduct: Product?,
    onSelectProduct: (Product) -> Unit,
    cartItems: Response<List<CartItem>>,
    orders: Response<List<OrderItem>>
) {
    val navigationType: RobinNavigationType = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            RobinNavigationType.NAVIGATION_DRAWER
        }

        WindowWidthSizeClass.Medium -> {
            RobinNavigationType.NAVIGATION_RAILS
        }

        WindowWidthSizeClass.Expanded -> {
            RobinNavigationType.NAVIGATION_RAILS
        }

        else -> {
            RobinNavigationType.NAVIGATION_DRAWER
        }
    }
    val appBarType = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> RobinAppBarType.COLLAPSING_APPBAR
        else -> RobinAppBarType.PERMANENT_APPBAR
    }
    RobinNavigationWrapper(
        navigationType = navigationType,
        profileUiState = profileUiState,
        signOut = signOut,
        userAuthenticated = userAuthenticated,
        productUiState = productUiState,
        appBarType = appBarType,
        categoriesUiState = categoriesUiState,
        brandsUiState = brandsUiState,
        onApply = onApply,
        filterState = filterState,
        onSelectProduct = onSelectProduct,
        selectedProduct = selectedProduct,
        cartItems = cartItems,
        orders=orders
    )
}


@Composable
fun RobinNavigationWrapper(
    navigationType: RobinNavigationType,
    profileUiState: ProfileData?,
    signOut: () -> Unit,
    userAuthenticated: Boolean,
    productUiState: Response<List<Product>>,
    appBarType: RobinAppBarType,
    categoriesUiState: Response<List<MainCategory>>,
    brandsUiState: Response<List<MainBrand>>,
    onApply: (QueryProduct) -> Unit,
    filterState: FilterState,
    selectedProduct: Product?,
    cartItems: Response<List<CartItem>>,
    onSelectProduct: (Product) -> Unit,
    orders: Response<List<OrderItem>>
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val state = rememberMessageBarState()
    val messageBarPosition =
        if (navigationType == RobinNavigationType.PERMANENT_NAVIGATION_DRAWER) MessageBarPosition.TOP
        else MessageBarPosition.BOTTOM

    val closeDrawer: () -> Unit = {
        scope.launch {
            drawerState.animateTo(
                DrawerValue.Closed,
                TweenSpec(
                    durationMillis = 400,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }
    val toggleDrawer: () -> Unit = {
        scope.launch {
            if (drawerState.isClosed)
                drawerState.animateTo(
                    DrawerValue.Open,
                    TweenSpec(
                        durationMillis = 400,
                        easing = LinearOutSlowInEasing
                    )
                )
            else drawerState.animateTo(
                DrawerValue.Closed,
                TweenSpec(
                    durationMillis=400,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }
    val showNavContent = remember {
        mutableStateOf(true)
    }
    val cartItemSize=(cartItems as? Response.Success)?.data?.size?:0

    when (navigationType) {
        RobinNavigationType.PERMANENT_NAVIGATION_DRAWER -> PermanentNavigationDrawer(
            drawerContent = {
                NavigationDrawer(
                    userAuthenticated = userAuthenticated,
                    navController = navController,
                    signOut = signOut,
                    closeDrawer = closeDrawer,
                    brandsUiState = brandsUiState,
                    categoriesUiState = categoriesUiState,
                    showNavContent = showNavContent,
                    onApply = onApply,
                    navigationType = navigationType,
                    filterState = filterState,
                    cartItemsSize= cartItemSize
                )
            },
            content = {
                MessageBarWrapper(
                    state = state,
                    position = messageBarPosition,
                    content = {
                        RobinNavHost(
                            navController = navController,
                            profileUiState = profileUiState,
                            toggleDrawer = toggleDrawer,
                            productUiState = productUiState,
                            messageBarState = state,
                            navigationType = navigationType,
                            appBarType = appBarType,
                            onSelectProduct = onSelectProduct,
                            selectedProduct = selectedProduct,
                            cartItems = cartItems,
                            orders=orders,
                            showNavContent = showNavContent
                        )
                    }
                )
            }
        )

        RobinNavigationType.NAVIGATION_RAILS -> {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet() {
                        NavigationDrawer(
                            userAuthenticated = userAuthenticated,
                            navController = navController,
                            signOut = signOut,
                            closeDrawer = closeDrawer,
                            brandsUiState = brandsUiState,
                            categoriesUiState = categoriesUiState,
                            showNavContent = showNavContent,
                            onApply = onApply,
                            navigationType = navigationType,
                            filterState = filterState,
                            cartItemsSize = cartItemSize,
                        )
                    }
                },
                content = {
                    Row {
                        NavigationRail {
                            NavigationRailsContent(
                                userAuthenticated = userAuthenticated,
                                navController = navController,
                                signOut = signOut
                            )
                        }
                        MessageBarWrapper(
                            state = state,
                            position = messageBarPosition,
                            content = {
                                RobinNavHost(
                                    navController = navController,
                                    profileUiState = profileUiState,
                                    toggleDrawer = toggleDrawer,
                                    productUiState = productUiState,
                                    messageBarState = state,
                                    navigationType = navigationType,
                                    appBarType = appBarType,
                                    cartItems = cartItems,
                                    selectedProduct = selectedProduct,
                                    onSelectProduct = onSelectProduct,
                                    showNavContent = showNavContent,
                                    orders = orders
                                )
                            }
                        )
                    }
                },
                drawerState = drawerState
            )
        }

        else -> ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    NavigationDrawer(
                        userAuthenticated = userAuthenticated,
                        navController = navController,
                        signOut = signOut,
                        closeDrawer = closeDrawer,
                        brandsUiState = brandsUiState,
                        categoriesUiState = categoriesUiState,
                        showNavContent = showNavContent,
                        onApply = onApply,
                        navigationType = navigationType,
                        filterState = filterState,
                        cartItemsSize = cartItemSize
                    )
                }
            },
            content = {
                MessageBarWrapper(
                    state = state,
                    position = messageBarPosition,
                    content = {
                        RobinNavHost(
                            navController = navController,
                            profileUiState = profileUiState,
                            toggleDrawer = toggleDrawer,
                            productUiState = productUiState,
                            messageBarState = state,
                            navigationType = navigationType,
                            appBarType = appBarType,
                            cartItems = cartItems,
                            selectedProduct = selectedProduct,
                            onSelectProduct = onSelectProduct,
                            showNavContent = showNavContent,
                            orders = orders
                        )
                    }
                )
            },
            drawerState = drawerState
        )
    }
}

@Composable
fun MessageBarWrapper(
    state: MessageBarState,
    content: @Composable () -> Unit,
    position: MessageBarPosition
) {
    ContentWithMessageBar(
        modifier = Modifier,
        messageBarState = state,
        position = position,
        content = content
    )
}

@Composable
fun RobinAppPreview(content: @Composable () -> Unit) {
    RobinTheme {
        content()
    }
}