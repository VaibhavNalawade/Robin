package com.vaibhav.robin.data.models

import androidx.annotation.Keep

@Keep
data class Favourite(
    val productID:String,
    val productSize: Int,
    val productVariant: String
)
