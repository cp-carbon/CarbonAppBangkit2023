package com.example.carbonapp.data.repository

import com.example.carbonapp.data.HttpRequester
import com.example.carbonapp.data.HttpResult
import com.example.carbonapp.data.response.LoginResponse
import com.example.carbonapp.`object`.LoggedInUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository private constructor() {

    companion object {
        val instance: LoginRepository by lazy { LoginRepository() }
    }

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // TODO: Load user credentials from shared preferences
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun login(email: String, password: String, callback: (HttpResult<LoggedInUser>) -> Unit) {
        HttpRequester.api.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.code() == 200) {
                    val user = LoggedInUser(response.body()!!.userId, response.body()!!.token, "John Doe", email)
                    setLoggedInUser(user)
                    callback(HttpResult.Success(user))
                } else {
                    callback(HttpResult.Error(Exception(response.errorBody()?.string())))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(HttpResult.Error(Exception(t)))
            }
        })
    }

    fun logout() {
        user = null

        // TODO: Delete user credentials in shared preferences
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser

        // TODO: Save user credentials in shared preferences
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}