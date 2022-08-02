package com.vaibhav.robin.entities.remote

import com.vaibhav.robin.entities.ui.model.Price

data class CartItems(
    val productId:String="",
    val productName:String?=null,
    val productImage:String?=null,
    val price: Price?=null,
    val productColor: Int =0,
    val productSize:String?=null,
    val productQuantity:Int=1
)