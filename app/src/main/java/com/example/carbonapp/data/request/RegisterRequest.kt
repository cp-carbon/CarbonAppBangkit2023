package com.example.carbonapp.data.request

import com.example.carbonapp.`object`.Vehicle

data class RegisterRequest(
    var fullName: String = "",
    var email: String = "",
    var password: String = "",
    val transportPreferences: ArrayList<String> = ArrayList(),
    var vehicle: Vehicle? = null,
)
