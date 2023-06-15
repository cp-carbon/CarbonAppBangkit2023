package com.example.carbonapp.data.repository

import com.example.carbonapp.data.HttpRequester
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.request.RegisterRequest
import com.example.carbonapp.data.response.RegisterResponse
import com.example.carbonapp.`object`.LoggedInUser
import com.example.carbonapp.`object`.Vehicle
import retrofit2.Call
import retrofit2.Callback

class RegisterRepository private constructor() {

    companion object {
        val instance: RegisterRepository by lazy { RegisterRepository() }
    }

    private val loginRepository: LoginRepository = LoginRepository.instance
    private var registerRequest: RegisterRequest

    init {
        registerRequest = RegisterRequest()
    }

    private val isBasicInformationFilled: Boolean
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
        registerRequest.vehicles.add(vehicle)
    }

    private fun clearRequest() {
        registerRequest = RegisterRequest()
    }

    fun register(callback: (HttpResult<LoggedInUser>) -> Unit) {
        if (!isBasicInformationFilled) {
            callback(HttpResult.Error(Exception("Fill basic information first!")))
        }

        val request = registerRequest.copy()
        HttpRequester.api.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: retrofit2.Response<RegisterResponse>
            ) {
                if (response.code() == 201) {
                    loginRepository.login(request.email, request.password) {
                        if (it is HttpResult.Success) {
                            clearRequest()
                        }
                        callback(it)
                    }
                } else {
                    callback(HttpResult.Error(Exception(response.errorBody()?.string())))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, e: Throwable) {
                callback(HttpResult.Error(Exception(e)))
            }
        })
    }
}