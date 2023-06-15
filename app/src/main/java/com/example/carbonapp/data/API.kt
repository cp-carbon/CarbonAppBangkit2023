package com.example.carbonapp.data

import com.example.carbonapp.data.request.LoginRequest
import com.example.carbonapp.data.request.RegisterRequest
import com.example.carbonapp.data.request.ScanFoodRequest
import com.example.carbonapp.data.response.AccountResponse
import com.example.carbonapp.data.response.HomeResponse
import com.example.carbonapp.data.response.LoginResponse
import com.example.carbonapp.data.response.MyActivityResponse
import com.example.carbonapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

sealed interface API {
    @POST("/signin")
    fun login(@Body loginRequest: LoginRequest) : Call<Response<LoginResponse>>

    @POST("/signup")
    fun register(@Body registerRequest: RegisterRequest) : Call<Response<RegisterResponse>>

    @GET("/{user_id}/home")
    fun home(@Path("user_id") userId: String) : Call<Response<HomeResponse>>

    @GET("/{user_id}/activity")
    fun activity(@Path("user_id") userId: String) : Call<Response<MyActivityResponse>>

    @GET("/{user_id}/account")
    fun account(@Path("user_id") userId: String) : Call<Response<AccountResponse>>

    @POST("/activities/food")
    fun scanFood(@Body scanFoodRequest: ScanFoodRequest) : Call<Response<String>>
}