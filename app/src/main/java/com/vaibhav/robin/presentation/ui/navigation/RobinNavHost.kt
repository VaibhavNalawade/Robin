package com.vaibhav.robin.presentation.ui.navigation


import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.RobinAppBarType
import com.vaibhav.robin.presentation.RobinNavigationType
import com.vaibhav.robin.presentation.models.state.CartUiState
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.ui.account.DateAndGenderSelect
import com.vaibhav.robin.presentation.ui.account.PersonalDetails
import com.vaibhav.robin.presentation.ui.account.ResetPassword
import com.vaibhav.robin.presentation.ui.account.SignIn
import com.vaibhav.robin.presentation.ui.account.SignUp
import com.vaibhav.robin.presentation.ui.cart.Cart
import com.vaibhav.robin.presentation.ui.cart.CartViewModel
import com.vaibhav.robin.presentation.ui.checkout.Checkout
import com.vaibhav.robin.presentation.ui.checkout.CheckoutDone
import com.vaibhav.robin.presentation.ui.home.Home
import com.vaibhav.robin.presentation.ui.orders.ManageOrders
import com.vaibhav.robin.presentation.ui.orders.OrderDetailsCompact
import com.vaibhav.robin.presentation.ui.product.ProductDetails
import com.vaibhav.robin.presentation.ui.review.Review
import com.vaibhav.robin.presentation.ui.search.SearchBar

@Composable
fun RobinNavHost(
    navController: NavHostController,
    profileUiState: ProfileData?,
    userAuthenticated: Boolean,
    toggleDrawer: () -> Unit,
    productUiState: Response<List<Product>>,
    messageBarState: MessageBarState,
    navigationType: RobinNavigationType,
    appBarType: RobinAppBarType,
    cartUiState: CartUiState,
    selectedProduct: Product?,
    onSelectProduct: (Product) -> Unit,
    showNavContent: MutableState<Boolean>,
    orders: Response<List<OrderItem>>
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = RobinDestinations.HOME,
        enterTransition = {
            fadeIn(animationSpec = tween(100))
        },
    ) {
        composable(RobinDestinations.HOME) {
            Home(
                navController = navController,
                profileUiState = profileUiState,
                toggleDrawer = toggleDrawer,
                productUiState = productUiState,
                messageBarState = messageBarState,
                navigationType = navigationType,
                appBarType = appBarType,
                showNavContent = showNavContent,
                onSelectProduct = onSelectProduct,
            )
        }

        composable(
            RobinDestinations.searchQuery("{query}"),
            listOf(navArgument("query") { type = NavType.StringType })
        ) { SearchBar(navController) }

        composable(RobinDestinations.CART) {
            if (userAuthenticated) {
                val viewModel: CartViewModel = hiltViewModel()
                viewModel.setMessageBarState(messageBarState)
                Cart(
                    cartUiState = cartUiState,
                    onBackNavigation = {
                        navController.popBackStack()
                    },
                    onRemoveCartItem = { cartId ->
                        viewModel.removeCartItem(cartId)
                    },
                    onCheckout = {
                        navController.navigate(RobinDestinations.CHECKOUT) {
                            popUpTo(RobinDestinations.CART)
                        }
                    },
                    onBrowse = {
                        navController.navigate(RobinDestinations.HOME) {
                            popUpTo(RobinDestinations.HOME)
                        }
                    },
                    retry = {}
                )

            } else navController.navigate(RobinDestinations.LOGIN_ROUTE)
        }

        composable(RobinDestinations.CHECKOUT) {
            Checkout(
                viewModel = hiltViewModel(),
                navController = navController,
                cartUiState = cartUiState,
                messageBarState = messageBarState
            )
        }
        composable(RobinDestinations.CHECKOUT_DONE) {
            CheckoutDone(navController = navController)
        }
        composable(
            RobinDestinations.REVIEW_SIGNATURE,
            listOf(navArgument("star") { type = NavType.IntType })
        ) {
            Review(
                navController = navController,
                viewModel = hiltViewModel(),
                selectProduct = selectedProduct!!
            )
        }
        composable(
            RobinDestinations.product("{Id}"),
            listOf(navArgument("Id") { type = NavType.StringType })
        ) {
            if (selectedProduct != null) {
                ProductDetails(
                    navController = navController,
                    viewModel = hiltViewModel(),
                    selectedProductUiState = selectedProduct,
                    cartItems = cartUiState as? CartUiState.Success,
                    messageBarState = messageBarState,
                )
            }
        }
        composable(RobinDestinations.MANAGE_ORDERS) {
            ManageOrders(
                navController = navController,
                orderItems = orders,
                hiltViewModel()
            )
        }
        composable(
            route = RobinDestinations.MANAGE_ORDERS_DETAILS_SIGNATURE,
            arguments = listOf(
                navArgument(RobinDestinations.MANAGE_ORDERS_DETAILS_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            OrderDetailsCompact(
                navController = navController,
                orders = (orders as? Response.Success)!!
            )
        }

        navigation(
            startDestination = RobinDestinations.LOGIN, route = RobinDestinations.LOGIN_ROUTE
        ) {

            composable(RobinDestinations.LOGIN) {
                SignIn(
                    navController = navController,
                    viewModel = hiltViewModel(),
                    messageBarState = messageBarState
                )
            }
            composable(RobinDestinations.SIGN_UP) {

                SignUp(
                    navController = navController,
                    viewModel = hiltViewModel(),
                    messageBarState = messageBarState
                )
            }
            composable(RobinDestinations.RESET_PASSWORD) {
                ResetPassword(
                    navController = navController,
                    viewModel = hiltViewModel(),
                    messageBarState = messageBarState
                )
            }
            composable(RobinDestinations.PERSONAL_DETAILS) {

                PersonalDetails(
                    navController = navController, viewModel = hiltViewModel()
                )
            }
            composable(RobinDestinations.DATE_AND_GENDER) {
                DateAndGenderSelect(
                    navController = navController, viewModel = hiltViewModel()
                )
            }
        }
    }
}
