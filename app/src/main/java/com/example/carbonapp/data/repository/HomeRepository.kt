package com.example.carbonapp.data.repository

import com.example.carbonapp.data.HttpRequester
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.response.HomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    companion object {
        val instance: HomeRepository by lazy { HomeRepository() }
    }

    var data: HomeResponse? = null
        private set

    fun fetchData(callback: (HttpResult<HomeResponse>) -> Unit) {
        val token = LoginRepository.instance.user?.token
        val userId = LoginRepository.instance.user?.userId

        if (token == null || userId == null) {
            callback(HttpResult.Error(Exception("User hasn't logged in yet.")))
            return
        }

        HttpRequester.api.home(token, userId).enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.code() == 200) {
                    callback(HttpResult.Success(response.body()!!))
                } else {
                    println(response.errorBody())
                    callback(HttpResult.Error(Exception(response.errorBody()?.string())))
                }
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                callback(HttpResult.Error(Exception(t)))
            }
        })
    }
}