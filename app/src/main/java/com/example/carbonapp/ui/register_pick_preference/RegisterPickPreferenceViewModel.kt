package com.example.carbonapp.ui.register_pick_preference

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.RegisterRepository

class RegisterPickPreferenceViewModel : ViewModel() {

    private val registerRepository = RegisterRepository.instance
    private val pickedVehiclePreferences = ArrayList<String>()

    fun isCarSelected(carText: String): Boolean = pickedVehiclePreferences.contains(carText)

    fun addPreference(preference: String) {
        pickedVehiclePreferences.add(preference)
    }

    fun clearPreferences() {
        pickedVehiclePreferences.clear()
    }

    fun savePreferences() {
        registerRepository.setVehiclePreferences(pickedVehiclePreferences)
    }
}