package com.example.carbonapp.data.repository

import com.example.carbonapp.data.HttpRequester
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.response.AccountResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountRepository private constructor() {

    companion object {
        val instance: AccountRepository by lazy { AccountRepository() }
    }

    var data: AccountResponse? = null
        private set

    fun fetchData(callback: (HttpResult<AccountResponse>) -> Unit) {
        val token = LoginRepository.instance.user?.token
        val userId = LoginRepository.instance.user?.userId

        if (token == null || userId == null) {
            callback(HttpResult.Error(Exception("User hasn't logged in yet.")))
            return
        }

        HttpRequester.api.account(token, userId).enqueue(object : Callback<AccountResponse> {
            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {
                if (response.code() == 200) {
                    data = response.body()
                    callback(HttpResult.Success(response.body()!!))
                } else {
                    println(response.errorBody())
                    callback(HttpResult.Error(Exception(response.errorBody()?.string())))
                }
            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                callback(HttpResult.Error(Exception(t)))
            }
        })
    }
}