package com.example.carbonapp.ui.account

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.repository.AccountRepository
import com.example.carbonapp.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AccountViewModel : ViewModel() {

    private val accountRepository = AccountRepository.instance
    private val loginRepository = LoginRepository.instance
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    fun load() {
        if (accountRepository.data != null) {
            val data = accountRepository.data!!

            _uiState.update {
                it.copy(
                    averageEmission = data.averageEmission,
                    profile = data.profile,
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        accountRepository.fetchData { response ->
            if (response is HttpResult.Success) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        averageEmission = response.data.averageEmission,
                        profile = response.data.profile,
                    )
                }
            }
            if (response is HttpResult.Error) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasError = true,
                        errorMessage = response.exception.message
                    )
                }
            }
        }
    }

    fun logout() {
        _uiState.update { it.copy(isLoading = true) }

        loginRepository.logout { response ->
            if (response is HttpResult.Success) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        isSuccessLogout = true,
                    )
                }
            }
            if (response is HttpResult.Error) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasError = true,
                        errorMessage = response.exception.message
                    )
                }
            }
        }
    }
}