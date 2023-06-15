package com.example.carbonapp.ui.register_car_setup

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.repository.RegisterRepository
import com.example.carbonapp.`object`.Vehicle

class RegisterCarSetupViewModel : ViewModel() {

    private val registerRepository = RegisterRepository.instance

    fun save(brand: String, className: String, model: String) {
        val car = Vehicle(brand, className, model)
        registerRepository.setVehicleDetail(car)
    }
}