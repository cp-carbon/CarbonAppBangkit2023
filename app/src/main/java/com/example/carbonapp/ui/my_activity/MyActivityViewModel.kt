package com.example.carbonapp.ui.my_activity

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.repository.MyActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyActivityViewModel : ViewModel() {

    private val myActivityRepository = MyActivityRepository.instance
    private val _uiState = MutableStateFlow(MyActivityUiState())
    val uiState: StateFlow<MyActivityUiState> = _uiState.asStateFlow()

    fun load() {
        if (myActivityRepository.data != null) {
            val data = myActivityRepository.data!!

            _uiState.update {
                it.copy(
                    averageEmission = data.averageEmission,
                    travelStats = data.travelStats,
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        myActivityRepository.fetchData { response ->
            if (response is HttpResult.Success) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        averageEmission = response.data.averageEmission,
                        travelStats = response.data.travelStats,
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