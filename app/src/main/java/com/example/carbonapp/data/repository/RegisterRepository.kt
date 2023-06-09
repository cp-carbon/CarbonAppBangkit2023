package com.example.carbonapp.data.repository

import com.example.carbonapp.data.HttpRequester
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.request.RegisterRequest
import com.example.carbonapp.data.response.RegisterGetVehiclesResponse
import com.example.carbonapp.data.response.RegisterResponse
import com.example.carbonapp.`object`.LoggedInUser
import com.example.carbonapp.`object`.Vehicle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                && registerRequest.rePassword.isNotBlank()

    fun setBasicInformation(fullName: String, email: String, password: String, rePassword: String) {
        registerRequest.apply {
            this.fullName = fullName
            this.email = email
            this.password = password
            this.rePassword = rePassword
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

    fun fetchVehicles(callback: (HttpResult<List<Vehicle>>) -> Unit) {
        HttpRequester.api.registerGetVehicles().enqueue(object : Callback<RegisterGetVehiclesResponse> {
            override fun onResponse(
                call: Call<RegisterGetVehiclesResponse>,
                response: Response<RegisterGetVehiclesResponse>
            ) {
                if (response.code() == 201) {
                    val data = response.body()!!.vehicles.map {
                        Vehicle(it.brand, it.className, it.model)
                    }
                    callback(HttpResult.Success(data))
                } else {
                    callback(HttpResult.Error(Exception(response.errorBody()?.string())))
                }
            }

            override fun onFailure(call: Call<RegisterGetVehiclesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun register(callback: (HttpResult<LoggedInUser>) -> Unit) {
        if (!isBasicInformationFilled) {
            callback(HttpResult.Error(Exception("Fill basic information first!")))
        }

        val request = registerRequest.copy()
        HttpRequester.api.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
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