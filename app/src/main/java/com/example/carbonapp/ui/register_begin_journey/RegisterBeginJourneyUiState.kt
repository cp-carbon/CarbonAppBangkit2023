package com.example.carbonapp.ui.register_begin_journey

data class RegisterBeginJourneyUiState(
    val isLoading: Boolean = false,
    val isSuccessRegister: Boolean = false,
    val errorMessage: String? = null,
)
