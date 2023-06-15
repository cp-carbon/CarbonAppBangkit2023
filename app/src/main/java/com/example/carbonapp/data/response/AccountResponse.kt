package com.example.carbonapp.data.response

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("average_emission") val averageEmission: Int,
    val profile: Profile,
) {
    inner class Profile(val fullname: String, val email: String)
}
