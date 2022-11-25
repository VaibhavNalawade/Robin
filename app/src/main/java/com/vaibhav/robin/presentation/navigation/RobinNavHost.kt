package com.vaibhav.robin.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.presentation.ui.account.AddressAndPhoneDetails
import com.vaibhav.robin.presentation.ui.account.DateAndGenderSelect
import com.vaibhav.robin.presentation.ui.account.Login
import com.vaibhav.robin.presentation.ui.account.PersonalDetails
import com.vaibhav.robin.presentation.ui.account.SignUp
import com.vaibhav.robin.presentation.ui.cart.Cart
import com.vaibhav.robin.presentation.ui.home.Home
import com.vaibhav.robin.presentation.ui.product.ProductDetails
import com.vaibhav.robin.presentation.ui.review.Review
import com.vaibhav.robin.presentation.ui.search.SearchBar

@Composable
fun RobinNavHost(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = RobinDestinations.HOME
    ) {
        composable(RobinDestinations.HOME) {
            Home(
                navController,
                hiltViewModel()
            )
        }

        composable(
            RobinDestinations.searchQuery("{query}"),
            listOf(navArgument("query") { type = NavType.StringType })
        ) { SearchBar(navController) }
        composable(RobinDestinations.CART) { Cart(hiltViewModel(),navController) }
        composable(
            RobinDestinations.REVIEW_SIGNATURE,
            listOf(
                navArgument("Id") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType },
                navArgument("star") { type = NavType.IntType }
            )
        ) {
            Review(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
        composable(
            RobinDestinations.product("{Id}"),
            listOf(navArgument("Id") { type = NavType.StringType })
        ) {
            ProductDetails(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
        navigation(
            startDestination = RobinDestinations.LOGIN, route = RobinDestinations.LOGIN_ROUTE
        ) {

            composable(RobinDestinations.LOGIN) {
                Login(
                    navController = navController, viewModel = hiltViewModel()
                )
            }
            composable(RobinDestinations.SIGN_UP) {

                SignUp(
                    navController = navController, viewModel = hiltViewModel()
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
