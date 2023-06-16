package com.example.carbonapp.ui.register_car_setup

data class RegisterCarSetupUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val brands: ArrayList<String> = arrayListOf(),
    val classes: ArrayList<String> = arrayListOf(),
    val models: ArrayList<String> = arrayListOf(),
)
