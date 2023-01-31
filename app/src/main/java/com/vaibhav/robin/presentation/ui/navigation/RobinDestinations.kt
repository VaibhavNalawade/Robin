package com.vaibhav.robin.presentation.ui.navigation

object RobinDestinations {
    const val HOME = "Home"
    private const val PRODUCT = "product"
    private const val SEARCH = "Search"
    const val CART = "Cart"
    const val LOGIN_ROUTE = "Login_Route"
    const val LOGIN = "Login"
    const val SIGN_UP = "SignUp"
    const val PERSONAL_DETAILS = "PersonalDetails"
    const val DATE_AND_GENDER = "DATE_AND_GENDER"
    const val ADDRESS_AND_PHONE = "ADDRESS_AND_PHONE"
    private const val REVIEW = "REVIEW"
    const val REVIEW_SIGNATURE="$REVIEW/{star}"

    fun review( star: String) =
        "$REVIEW/$star"

    fun review( star: Int) =
        "$REVIEW/$star"

    fun product(Id: String) = "$PRODUCT/$Id"
    fun searchQuery(query: String) = "$SEARCH/$query"
}