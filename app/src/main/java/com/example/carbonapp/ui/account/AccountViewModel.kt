package com.example.carbonapp.ui.account

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AccountViewModel : ViewModel() {

    private val loginRepository = LoginRepository.instance
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    fun logout() {
        loginRepository.logout { response ->
            _uiState.update {
                it.copy(isSuccessLogout = response is HttpResult.Success)
            }
        }
    }
}