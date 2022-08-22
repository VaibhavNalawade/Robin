package com.vaibhav.robin.data.models

data class Media(
    val images: List<String> = emptyList(),
    val videos: List<String> = emptyList(),
    val selection: String = ""
)
