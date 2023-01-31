package com.vaibhav.robin.presentation.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.RobinAppBarType
import com.vaibhav.robin.presentation.RobinNavigationType
import com.vaibhav.robin.presentation.models.state.FilterState
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.ui.account.AddressAndPhoneDetails
import com.vaibhav.robin.presentation.ui.account.DateAndGenderSelect
import com.vaibhav.robin.presentation.ui.account.SignIn
import com.vaibhav.robin.presentation.ui.account.PersonalDetails
import com.vaibhav.robin.presentation.ui.account.SignUp
import com.vaibhav.robin.presentation.ui.cart.Cart
import com.vaibhav.robin.presentation.ui.home.Home
import com.vaibhav.robin.presentation.ui.product.ProductDetails
import com.vaibhav.robin.presentation.ui.review.Review
import com.vaibhav.robin.presentation.ui.search.SearchBar

@Composable
fun RobinNavHost(
    navController: NavHostController,
    profileUiState: ProfileData?,
    toggleDrawer: () -> Unit,
    productUiState: Response<List<Product>>,
    messageBarState: MessageBarState,
    navigationType: RobinNavigationType,
    appBarType: RobinAppBarType,
    cartItems: Response<List<CartItem>>,
    selectedProduct: Product?,
    onSelectProduct: (Product) -> Unit,
    filter: MutableState<Boolean>
) {
    NavHost(
        navController = navController, startDestination = RobinDestinations.HOME
    ) {
        composable(RobinDestinations.HOME) {
            Home(
                navController = navController,
                profileUiState = profileUiState,
                toggleDrawer =toggleDrawer,
                productUiState =productUiState,
                messageBarState =messageBarState,
                navigationType =navigationType,
                appBarType =appBarType,
                filter=filter,
                onSelectProduct=onSelectProduct,
            )
        }

        composable(
            RobinDestinations.searchQuery("{query}"),
            listOf(navArgument("query") { type = NavType.StringType })
        ) { SearchBar(navController) }

        composable(RobinDestinations.CART) {
            Cart(
                viewModel = hiltViewModel(),
                navController = navController,
                cartItems=cartItems
            )
        }
        composable(
            RobinDestinations.REVIEW_SIGNATURE,
            listOf(navArgument("star") { type = NavType.IntType })
        ) {
            Review(
                navController = navController,
                viewModel = hiltViewModel(),
                selectProduct=selectedProduct!!
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
                    cartItems = cartItems,
                    messageBarState =messageBarState,
                )
            }
        }
        navigation(
            startDestination = RobinDestinations.LOGIN, route = RobinDestinations.LOGIN_ROUTE
        ) {

            composable(RobinDestinations.LOGIN) {
                SignIn(
                    navController = navController,
                    viewModel = hiltViewModel(),
                    messageBarState=messageBarState,
                    navigationType=navigationType
                )
            }
            composable(RobinDestinations.SIGN_UP) {

                SignUp(
                    navController = navController,
                    viewModel = hiltViewModel(),
                    messageBarState=messageBarState,
                    navigationType=navigationType
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
            composable(RobinDestinations.ADDRESS_AND_PHONE) {

                AddressAndPhoneDetails(
                    navController = navController, viewModel = hiltViewModel()
                )
            }
        }
    }
}
