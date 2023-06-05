package com.vaibhav.robin.data.models

import androidx.annotation.Keep
import com.vaibhav.robin.presentation.Order
@Keep
data class QueryProduct(
    val brandId:String?=null,
    val categoryId:String?=null,
    val order:Order?=null
)
