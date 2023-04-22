package com.vaibhav.robin.presentation.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

object RobinDestinations {
    const val HOME = "Home"
    private const val PRODUCT = "product"
    private const val SEARCH = "Search"
    const val CART = "Cart"
    const val LOGIN_ROUTE = "Login_Route"
    const val LOGIN = "Login"
    const val SIGN_UP = "SignUp"
    const val RESET_PASSWORD="ResetPassword"
    const val PERSONAL_DETAILS = "PersonalDetails"
    const val DATE_AND_GENDER = "DATE_AND_GENDER"
    const val ADDRESS_AND_PHONE = "ADDRESS_AND_PHONE"
    private const val REVIEW = "REVIEW"
    const val REVIEW_SIGNATURE = "$REVIEW/{star}"

    fun review(star: String) =
        "$REVIEW/$star"

    fun review(star: Int) =
        "$REVIEW/$star"

    fun product(id: String) = "$PRODUCT/$id"
    fun searchQuery(query: String) = "$SEARCH/$query"
    const val CHECKOUT = "Checkout"
    const val CHECKOUT_DONE="CheckoutDone"
    const val MANAGE_ORDERS="ManageOrders"

    private const val MANAGE_ORDERS_DETAILS="ManageOrdersDetails"
    const val MANAGE_ORDERS_DETAILS_ARG="id"
    const val MANAGE_ORDERS_DETAILS_SIGNATURE="$MANAGE_ORDERS/{$MANAGE_ORDERS_DETAILS_ARG}"
    fun manageOrdersDetails(id:String)="$MANAGE_ORDERS/$id"


}