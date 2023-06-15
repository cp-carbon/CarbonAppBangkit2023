package com.example.carbonapp.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user_id") val userId: String,
    val token: String,
)
