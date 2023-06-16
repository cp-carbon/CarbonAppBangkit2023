package com.example.carbonapp.data.response

import com.google.gson.annotations.SerializedName

data class RegisterGetVehiclesResponse(
    val vehicles: ArrayList<VehicleResponse>
) {
    data class VehicleResponse(
        val id: String,
        @SerializedName("brand_name") val brand: String,
        val model: String,
        @SerializedName("class") val className: String,
        val emission: Int,
    )
}
