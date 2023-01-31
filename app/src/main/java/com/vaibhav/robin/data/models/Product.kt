package com.vaibhav.robin.data.models


data class Product(
    val brandId: String = "",
    val brandName: String = "",
    val brandLogo: String = "",
    val categoryId: String = "",
    val categoryName: String = "",
    val description: String = "",
    val minPrice: Double = 0.0,
    val maxPrice: Double = 0.0,
    val ratingStars:Float?=null,
    val ratingCount:Int=0,
    val name: String = "",
    val id: String = "",
    val details: List<Map<String, String>> = emptyList(),
    val subDescription: List<Map<String, String>> = emptyList(),
    val media: Map<String, List<String>> = emptyMap(),
    val sizes: Map<String, List<Map<String, Any>>> = emptyMap(),
    val status: String = "",
    val variantIndex: List<String> = emptyList()
)
