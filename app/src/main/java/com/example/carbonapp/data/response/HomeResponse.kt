package com.example.carbonapp.data.response

import com.example.carbonapp.`object`.News

data class HomeResponse(
    val todayEmission: Int,
    val travelActivities: ArrayList<String>,
    val news: ArrayList<News>,
    val products: ArrayList<String>,
)
