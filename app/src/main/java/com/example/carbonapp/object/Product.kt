package com.example.carbonapp.`object`

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("background") val backgroundImageUrl: String,
    val title: String,
    val url: String,
)