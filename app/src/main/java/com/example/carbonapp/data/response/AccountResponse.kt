package com.example.carbonapp.data.response

data class AccountResponse(
    val averageEmission: Int,
    val profile: Profile,
) {
    inner class Profile(val name: String, val email: String)
}
