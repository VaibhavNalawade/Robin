package com.vaibhav.robin.data.unsplash.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Urls(

    @SerialName("raw") var raw: String? = null,
    @SerialName("full") var full: String? = null,
    @SerialName("regular") var regular: String? = null,
    @SerialName("small") var small: String? = null,
    @SerialName("thumb") var thumb: String? = null,
    @SerialName("small_s3") var smallS3: String? = null

)