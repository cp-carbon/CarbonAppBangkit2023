package com.example.carbonapp.ui.register_car_setup

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.repository.RegisterRepository
import com.example.carbonapp.`object`.Vehicle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterCarSetupViewModel : ViewModel() {

    private val registerRepository = RegisterRepository.instance
    private val _uiState = MutableStateFlow(RegisterCarSetupUiState())
    val uiState: StateFlow<RegisterCarSetupUiState> = _uiState.asStateFlow()

    fun load() {
        _uiState.update { it.copy(isLoading = true) }

        registerRepository.fetchVehicles { response ->
            if (response is HttpResult.Success) {
                val brands = arrayListOf<String>("Suzuki")
                val classes = arrayListOf<String>("Class A")
                val models = arrayListOf<String>("Class B")

                response.data.forEach {
                    brands.add(it.brand)
                    classes.add(it.className)
                    models.add(it.model)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        brands = brands,
                        classes = classes,
                        models = models,
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

    fun save(brand: String, className: String, model: String) {
        val car = Vehicle(brand, className, model)
        registerRepository.setVehicleDetail(car)
    }
}