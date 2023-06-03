package com.example.carbonapp.data

import com.example.carbonapp.data.request.LoginRequest
import com.example.carbonapp.data.response.LoginResponse
import com.example.carbonapp.`object`.LoggedInUser

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

    fun login(email: String, password: String, callback: (Response<LoggedInUser>) -> Unit) {
        try {
            // TODO: Handle user authentication
            val request = LoginRequest(email, password)
            // simulate success login
            val response = Response.Success(200, "success", LoginResponse("u-001", "token"))

            if (response.code == 200) {
                val fakeUser = LoggedInUser(response.data.userId, response.data.token, "John Doe", email)
                setLoggedInUser(fakeUser)
                callback(Response.Success(response.code, response.status, fakeUser))
            }
        } catch (e: Throwable) {
            callback(Response.Error(0, "Connection Error", Exception(e)))
        }
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