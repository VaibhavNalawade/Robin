package com.vaibhav.robin.data.unsplash.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ProfileImage(

    @SerialName("small") var small: String? = null,
    @SerialName("medium") var medium: String? = null,
    @SerialName("large") var large: String? = null

)