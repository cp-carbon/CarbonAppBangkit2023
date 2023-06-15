package com.example.carbonapp.data

import retrofit2.Retrofit

class HttpRequester {

    companion object {
        val BASE_URL = "http://your_url.com"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build()
        }

        val api: API by lazy { retrofit.create(API::class.java) }
    }
}