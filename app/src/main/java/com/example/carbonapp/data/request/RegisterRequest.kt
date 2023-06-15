package com.example.carbonapp.data.request

import com.example.carbonapp.`object`.Vehicle
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("fullname") var fullName: String = "",
    var email: String = "",
    var password: String = "",
    @SerializedName("confirm_password") var rePassword: String = "",
    @SerializedName("transport_preferences") val transportPreferences: ArrayList<String> = arrayListOf(),
    val vehicles: ArrayList<Vehicle> = arrayListOf(),
)
