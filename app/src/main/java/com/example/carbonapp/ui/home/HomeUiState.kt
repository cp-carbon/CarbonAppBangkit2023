package com.example.carbonapp.ui.home

import com.example.carbonapp.`object`.News

data class HomeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val todayEmission: Int = 0,
    val travelActivities: ArrayList<String> = ArrayList(),
    val news: ArrayList<News> = ArrayList(),
    val products: ArrayList<String> = ArrayList(),
)
