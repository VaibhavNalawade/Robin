package com.vaibhav.robin.data.models

data class Size(
    val price:Price=Price(),
    val size:String="",
    val Stock:Int=-1
)
