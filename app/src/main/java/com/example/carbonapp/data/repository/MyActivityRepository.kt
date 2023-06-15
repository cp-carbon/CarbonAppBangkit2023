package com.example.carbonapp.data.repository

import com.example.carbonapp.data.HttpRequester
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.response.MyActivityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyActivityRepository private constructor() {

    companion object {
        val instance: MyActivityRepository by lazy { MyActivityRepository() }
    }

    var data: MyActivityResponse? = null
        private set

    fun fetchData(callback: (HttpResult<MyActivityResponse>) -> Unit) {
        val token = LoginRepository.instance.user?.token
        val userId = LoginRepository.instance.user?.userId

        if (token == null || userId == null) {
            callback(HttpResult.Error(Exception("User hasn't logged in yet.")))
            return
        }

        HttpRequester.api.myActivity(token, userId).enqueue(object : Callback<MyActivityResponse> {
            override fun onResponse(call: Call<MyActivityResponse>, response: Response<MyActivityResponse>) {
                if (response.code() == 200) {
                    data = response.body()
                    callback(HttpResult.Success(response.body()!!))
                } else {
                    println(response.errorBody())
                    callback(HttpResult.Error(Exception(response.errorBody()?.string())))
                }
            }

            override fun onFailure(call: Call<MyActivityResponse>, t: Throwable) {
                callback(HttpResult.Error(Exception(t)))
            }
        })
    }
}