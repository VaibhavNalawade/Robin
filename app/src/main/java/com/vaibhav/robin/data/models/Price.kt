package com.vaibhav.robin.data.models

data class Price(
    val discountAvailable: Boolean = false,
    val discountedPrice: Double = 0.0,
    val retail: Double = 0.0,
)