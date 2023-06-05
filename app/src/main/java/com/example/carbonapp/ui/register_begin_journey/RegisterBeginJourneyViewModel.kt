package com.example.carbonapp.ui.register_begin_journey

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.RegisterRepository
import com.example.carbonapp.data.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterBeginJourneyViewModel : ViewModel() {

    private val registerRepository = RegisterRepository.instance
    private val _uiState = MutableStateFlow(RegisterBeginJourneyUiState())
    val uiState: StateFlow<RegisterBeginJourneyUiState> = _uiState.asStateFlow()

    fun register() {
        _uiState.update { it.copy(isLoading = true) }

        registerRepository.register { response ->
            if (response is Response.Success) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccessRegister = true,
                        errorMessage = null
                    )
                }
            }
            if (response is Response.Error) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccessRegister = false,
                        errorMessage = response.exception.message
                    )
                }
            }
        }
    }
}