package com.vaibhav.robin.navigation


import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.presentation.account.*
import com.vaibhav.robin.presentation.cart.Cart
import com.vaibhav.robin.presentation.home.Home
import com.vaibhav.robin.presentation.product.ProductDetails
import com.vaibhav.robin.presentation.search.SearchBar
import androidx.hilt.navigation.compose.hiltViewModel
@Composable
fun RobinNavHost(navController: NavHostController) {

    val snackBarHostState = remember { SnackbarHostState() }
    NavHost(
        navController = navController, startDestination = RobinDestinations.HOME
    ) {
        composable(RobinDestinations.HOME) { Home(navController, snackBarHostState) }

        composable(
            RobinDestinations.searchQuery("{query}"),
            listOf(navArgument("query") { type = NavType.StringType })
        ) { SearchBar(navController) }

        composable(RobinDestinations.CART) { Cart(navController, snackBarHostState) }


        composable(
            RobinDestinations.product("{Id}"),
            listOf(navArgument("Id") { type = NavType.StringType })
        ) { ProductDetails(navController, snackBarHostState) }
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
                    navController = navController, viewModel = viewModel()
                )
            }
            composable(RobinDestinations.DATE_AND_GENDER) {

                DateAndGenderSelect(
                    navController = navController, viewModel = viewModel()
                )
            }
            composable(RobinDestinations.ADDRESS_AND_PHONE) {

                AddressAndPhoneDetails(
                    navController = navController, viewModel = viewModel()
                )
            }
        }
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.e("NAV", destination.route!!)
            when (destination.route) {
                RobinDestinations.CART -> {
                    if (!userExist()) {
                        navController.navigate(RobinDestinations.LOGIN_ROUTE)
                    }
                }
            }
        }
    }
}

fun userExist() = Firebase.auth.currentUser != null


object RobinDestinations {
    const val HOME = "home"
    private const val PRODUCT = "product"
    private const val SEARCH = "Search"
    const val CART = "Cart"
    const val LOGIN_ROUTE = "Login_Route"
    const val LOGIN = "Login"
    const val SIGN_UP = "SignUp"
    const val PERSONAL_DETAILS = "PersonalDetails"
    const val DATE_AND_GENDER = "DATE_AND_GENDER"
    const val ADDRESS_AND_PHONE = "ADDRESS_AND_PHONE"

    fun product(Id: String) = "$PRODUCT/$Id"
    fun searchQuery(query: String) = "$SEARCH/$query"
}