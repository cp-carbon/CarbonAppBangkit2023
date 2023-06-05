package com.example.carbonapp.data

import com.example.carbonapp.`object`.LoggedInUser
import com.example.carbonapp.data.request.RegisterRequest
import com.example.carbonapp.`object`.Vehicle

class RegisterRepository private constructor() {

    companion object {
        val instance: RegisterRepository by lazy { RegisterRepository() }
    }

    private val loginRepository: LoginRepository = LoginRepository.instance
    private var registerRequest: RegisterRequest

    init {
        registerRequest = RegisterRequest()
    }

    val isBasicInformationFilled: Boolean
        get() = registerRequest.fullName.isNotBlank()
                && registerRequest.email.isNotBlank()
                && registerRequest.password.isNotBlank()

    fun setBasicInformation(fullName: String, email: String, password: String) {
        registerRequest.apply {
            this.fullName = fullName
            this.email = email
            this.password = password
        }
    }

    fun setVehiclePreferences(preferences: List<String>) {
        registerRequest.transportPreferences.apply {
            clear()
            addAll(preferences)
        }
    }

    fun setVehicleDetail(vehicle: Vehicle) {
        registerRequest.vehicle = vehicle
    }

    private fun clearRequest() {
        registerRequest = RegisterRequest()
    }

    fun register(callback: (Response<LoggedInUser>) -> Unit) {
        try {
            // TODO: Handle user registration
            val request = registerRequest.copy()
            // simulate success register
            val response = Response.Success(200, "success", "message")

            if (response.code == 200) {
                loginRepository.login(request.email, request.password) {
                    if (it is Response.Success) {
                        clearRequest()
                    }
                    callback(it)
                }
            } else {
                callback(Response.Error(response.code, response.status, Exception(response.data)))
            }
        } catch (e: Throwable) {
            callback(Response.Error(0, "Connection Error", Exception(e)))
        }
    }
}