package com.example.carbonapp.ui.my_activity

import com.example.carbonapp.`object`.TravelStats

data class MyActivityUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val averageEmission: Int = 0,
    val travelStats: TravelStats? = null,
)
