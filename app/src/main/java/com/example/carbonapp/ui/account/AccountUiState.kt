package com.example.carbonapp.ui.account

import com.example.carbonapp.data.response.AccountResponse

data class AccountUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val averageEmission: Int = 0,
    val profile: AccountResponse.Profile? = null,
    val isSuccessLogout: Boolean = false,
)
