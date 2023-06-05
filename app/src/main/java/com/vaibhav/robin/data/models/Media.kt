package com.vaibhav.robin.data.models

import androidx.annotation.Keep

@Keep
data class Media(
    val images: List<String> = emptyList(),
    val videos: List<String> = emptyList(),
    val selection: String = ""
)
