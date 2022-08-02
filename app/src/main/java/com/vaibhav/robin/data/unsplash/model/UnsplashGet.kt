package com.vaibhav.robin.data.unsplash.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UnsplashGet(

    @SerialName("total") var total: Int? = null,
    @SerialName("total_pages") var totalPages: Int? = null,
    @SerialName("results") var results: ArrayList<Results> = arrayListOf()

)