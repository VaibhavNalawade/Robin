package com.vaibhav.robin.data.models

data class Price(
    val discount: Boolean = false,
    val discountedPrice: Double = 0.0,
    val price: Double = 0.0,
)