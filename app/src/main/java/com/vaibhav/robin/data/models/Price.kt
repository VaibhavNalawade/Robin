package com.vaibhav.robin.data.models

data class Price(
    val currency: String = "",
    val price: Double = 0.0,
    val shipping: Double = 0.0,
    val tax: Double = 0.0
)