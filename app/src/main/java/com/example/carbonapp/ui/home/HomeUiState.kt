package com.example.carbonapp.ui.home

import com.example.carbonapp.`object`.News
import com.example.carbonapp.`object`.Product
import com.example.carbonapp.`object`.TravelStats

data class HomeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val todayEmission: Int = 0,
    val travelActivities: TravelStats? = null,
    val news: ArrayList<News> = ArrayList(),
    val products: ArrayList<Product> = ArrayList(),
)
