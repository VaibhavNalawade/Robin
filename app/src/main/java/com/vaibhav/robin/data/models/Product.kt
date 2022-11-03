package com.vaibhav.robin.data.models


data class Product(
    val brand: Brand = Brand(),
    val rating:Rating=Rating(),
    val category: Category = Category(),
    val description:String="",
    val name: String = "",
    val id:String="",
    val variant: List<Variant> = emptyList(),
    val reference:List<Reference> = emptyList(),
    val details: List<Map<String,String>> = emptyList(),
    val subDescription:List<Map<String,String>> = emptyList()
)