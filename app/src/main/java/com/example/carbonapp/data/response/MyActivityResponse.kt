package com.example.carbonapp.data.response

import com.example.carbonapp.`object`.TravelStats

data class MyActivityResponse(
    val averageEmission: Int,
    val emissionGenerated: List<Int>,
    val travelStats: TravelStats,
)
