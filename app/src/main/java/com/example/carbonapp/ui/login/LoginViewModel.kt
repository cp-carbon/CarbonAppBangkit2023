package com.example.carbonapp.ui.login

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository.instance
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun isLoggedIn(): Boolean {
        return loginRepository.isLoggedIn
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Email and Password can't be empty")
            }
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        loginRepository.login(email, password) { result ->
            if (result is HttpResult.Success) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccess = true,
                        errorMessage = null
                    )
                }
            }
            if (result is HttpResult.Error) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccess = false,
                        errorMessage = result.exception.message
                    )
                }
            }
        }
    }

    // TODO: Implement login with google
    fun loginWithGoogle() {
        throw NotImplementedError()
    }
}