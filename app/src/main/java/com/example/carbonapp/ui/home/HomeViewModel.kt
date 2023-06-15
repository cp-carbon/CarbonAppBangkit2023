package com.example.carbonapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.repository.HomeRepository
import com.example.carbonapp.data.HttpResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val homeRepository = HomeRepository.instance
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun load() {
        if (homeRepository.data != null) {
            val data = homeRepository.data!!

            _uiState.update {
                it.copy(
                    isLoading = false,
                    hasError = false,
                    todayEmission = data.todayEmission,
                    travelActivities = data.travelActivities,
                    news = data.news,
                    products = data.products,
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        homeRepository.fetchData { response ->
            if (response is HttpResult.Success) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        todayEmission = response.data.todayEmission,
                        travelActivities = response.data.travelActivities,
                        news = response.data.news,
                        products = response.data.products,
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