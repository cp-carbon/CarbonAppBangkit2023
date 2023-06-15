package com.example.carbonapp.data.request

data class ScanFoodRequest(
    val userId: String,
    val item: String,
    val emission: String,
)