package com.example.carbonapp.`object`

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val token: String,
    val fullName: String,
    val email: String
)