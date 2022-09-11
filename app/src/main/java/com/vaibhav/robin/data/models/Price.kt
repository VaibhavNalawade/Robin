package com.vaibhav.robin.data.models

data class Price(
    val discountAvailable: Boolean = false,
    val discounted: Double = 0.0,
    val retail: Double = 0.0,
)