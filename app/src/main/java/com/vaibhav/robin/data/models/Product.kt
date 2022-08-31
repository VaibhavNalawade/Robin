package com.vaibhav.robin.data.models


data class Product(
    val brand: Brand = Brand(),
    val category: Category = Category(),
    val description:String="",
    val name: String = "",
    val variant: List<Variant> = emptyList(),
    val reference:List<Reference> = emptyList(),
    val details: List<Map<String,String>> = emptyList(),
    val subDescription:List<Map<String,String>> = emptyList()
)

data class Variant(
    val media: Media = Media(),
    val name: String = "",
    val size: List<Size> = emptyList(),

)