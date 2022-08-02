package com.vaibhav.robin.entities.remote

data class Comment(
    val firstName: String = "",
    val profileImg: String = "",
    val lastName: String = "",
    val likes: Int = 0,
    val date: String = "",
    val rate: Float = 0f,
    val headline: String = "",
    val content: String = ""
)