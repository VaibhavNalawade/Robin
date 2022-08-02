package com.vaibhav.robin.entities.ui.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName

data class Product(
    val brand: Brand = Brand(),
    val category: Category = Category(),
    val name: String = "",
    val type: List<SubProductType> = emptyList()
)
data class Brand(
    val name: String="",
    val url: String="",
    val ref: DocumentReference?=null
)
data class Category(
    val name:String?=null,
    val ref: DocumentReference?=null
)
data class SubProductType(
    val size: List<String> = emptyList(),
    val price: Price = Price(),
    val name: String = "",
    val media: Media = Media(),
    @set:PropertyName("expect_type") @get:PropertyName("expect_type")var expectType: Int = 0,
    val details: List<DetailsPoint> = emptyList(),
    val description: Description = Description()
)
data class Description(
    val description:String?=null,
    @set:PropertyName("sub_des") @get:PropertyName("sub_des") var subDescription:List<DetailsPoint> = emptyList()
)

data class DetailsPoint(
    @set:PropertyName("1") @get:PropertyName("1") var one: String? = null,
    @set:PropertyName("2") @get:PropertyName("2") var two: String? = null,
    @set:PropertyName("3") @get:PropertyName("3") var three: String? = null,
    @set:PropertyName("4") @get:PropertyName("4") var four: String? = null,
    @set:PropertyName("5") @get:PropertyName("5") var five: String? = null,
    @set:PropertyName("6") @get:PropertyName("6") var six: String? = null,
    @set:PropertyName("7") @get:PropertyName("7") var seven: String? = null,
    @set:PropertyName("8") @get:PropertyName("8") var eight: String? = null,
    @set:PropertyName("9") @get:PropertyName("9") var nine: String? = null,
    @set:PropertyName("10") @get:PropertyName("10") var ten: String? = null
)

data class Media(
    val images: List<String> = emptyList(),
    val videos: List<String> = emptyList(),
    val selection: String = ""
)

data class Price(
    val currency: String = "",
    val price: Double = 0.0,
    val shipping: Double = 0.0,
    val tax: Double = 0.0
)