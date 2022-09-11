package com.vaibhav.robin.data.models

data class Variant(
    val media: Media = Media(),
    val name: String = "",
    val size: List<Size> = emptyList(),
)