package com.example.carbonapp.`object`

import com.google.gson.annotations.SerializedName

data class News(
    val author: String,
    val publishedAt: String,
    val title: String,
    val description: String,
    val url: String,
    @SerializedName("background_image") val backgroundImageUrl: String?,
)