package com.example.carbonapp.data.response

import com.example.carbonapp.`object`.TravelStats
import com.google.gson.annotations.SerializedName

data class MyActivityResponse(
    @SerializedName("average_emission") val averageEmission: Int,
    @SerializedName("travel_stats") val travelStats: TravelStats,
)
