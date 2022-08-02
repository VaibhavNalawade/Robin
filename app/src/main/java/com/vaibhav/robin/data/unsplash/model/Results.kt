package com.vaibhav.robin.data.unsplash.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Results(

    @SerialName("id") var id: String? = null,
    @SerialName("width") var width: Int? = null,
    @SerialName("height") var height: Int? = null,
    @SerialName("color") var color: String? = null,
    @SerialName("blur_hash") var blurHash: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("alt_description") var altDescription: String? = null,
    @SerialName("urls") var urls: Urls? = Urls(),
    @SerialName("categories") var categories: ArrayList<String> = arrayListOf(),
    @SerialName("likes") var likes: Int? = null,
    @SerialName("user") var user: User? = User(),

)