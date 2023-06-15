package com.example.carbonapp.data.response

import com.example.carbonapp.`object`.News
import com.example.carbonapp.`object`.Product
import com.example.carbonapp.`object`.TravelStats
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("today_emission") val todayEmission: Int,
    @SerializedName("travel_activities") val travelActivities: TravelStats,
    val news: ArrayList<News>,
    val products: ArrayList<Product>,
)
