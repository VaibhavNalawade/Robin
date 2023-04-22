package com.vaibhav.robin.data.models

import androidx.annotation.Keep

@Keep

data class CartItem(
    val cartId: String = "",
    val productId: String = "",
    val productName: String = "",
    val productVariant: String = "",
    val productSize: Int = 0,
    val productImage: String? = null,
    val price: Double = 0.0,
    val brandName: String = "",
    val brandLogo: String = ""
)
