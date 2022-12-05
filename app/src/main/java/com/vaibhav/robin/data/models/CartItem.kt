package com.vaibhav.robin.data.models

data class CartItem(
    val cartId:String="",
    val productId: String="",
    val productName: String="",
    val productVariant: Int=0,
    val productSize: Int=0,
    val productImage:String?=null,
    val brand: Brand?=null,
    val price: Double=0.0,
)
