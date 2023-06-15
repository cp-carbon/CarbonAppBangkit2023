package com.example.carbonapp.data

import com.example.carbonapp.data.request.RegisterRequest
import com.example.carbonapp.data.request.ScanFoodRequest
import com.example.carbonapp.data.response.AccountResponse
import com.example.carbonapp.data.response.HomeResponse
import com.example.carbonapp.data.response.LoginResponse
import com.example.carbonapp.data.response.MyActivityResponse
import com.example.carbonapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

sealed interface API {
    @FormUrlEncoded
    @POST("/signin")
    fun login(@Field("email") email: String, @Field("password") password: String) : Call<LoginResponse>

    @POST("/signup")
    fun register(@Body registerRequest: RegisterRequest) : Call<RegisterResponse>

    @GET("/{user_id}/home")
    fun home(@Header("token") token: String, @Path("user_id") userId: String) : Call<HomeResponse>

    @GET("/{user_id}/activity")
    fun myActivity(@Header("token") token: String, @Path("user_id") userId: String) : Call<MyActivityResponse>

    @GET("/{user_id}/account")
    fun account(@Header("token") token: String, @Path("user_id") userId: String) : Call<AccountResponse>

    @POST("/activities/food")
    fun scanFood(@Header("token") token: String, @Body scanFoodRequest: ScanFoodRequest) : Call<String>
}