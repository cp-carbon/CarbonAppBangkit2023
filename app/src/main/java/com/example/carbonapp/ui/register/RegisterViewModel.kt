package com.example.carbonapp.ui.register

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.RegisterRepository

class RegisterViewModel : ViewModel() {

    private val registerRepository = RegisterRepository.instance

    fun validate(fullName: String, email: String, password: String, confirmPassword: String): String? {
        if (fullName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            return "all fields have to be filled"
        }
        if (!email.contains("@")) {
            return "wrong format for email"
        }
        if (password != confirmPassword) {
            return "password doesn't match"
        }

        return null
    }

    fun save(fullName: String, email: String, password: String) {
        registerRepository.setBasicInformation(fullName, email, password)
    }
}