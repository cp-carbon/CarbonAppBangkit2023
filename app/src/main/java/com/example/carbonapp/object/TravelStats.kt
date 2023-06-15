package com.example.carbonapp.`object`

import com.google.gson.annotations.SerializedName

data class TravelStats(
    val walk: Int,
    val bike: Int,
    val vehicle: Int,
    @SerializedName("public_transport") val publicTransport: Int,
)
