package com.example.carbonapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.HomeRepository
import com.example.carbonapp.data.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val homeRepository = HomeRepository.instance
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun load() {
        _uiState.update { it.copy(isLoading = true) }

        homeRepository.fetchData { response ->
            if (response is Response.Success) {
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
            if (response is Response.Error) {
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