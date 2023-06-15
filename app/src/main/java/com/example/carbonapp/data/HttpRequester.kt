package com.example.carbonapp.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpRequester {

    companion object {
        private const val BASE_URL = "https://d1b8-2001-448a-6000-b802-689f-9984-bf46-9c3.ngrok-free.app"

        private fun client(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

        private val gson = GsonBuilder()
            .setLenient()
            .create()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client())
                .build()
        }

        val api: API by lazy { retrofit.create(API::class.java) }
    }
}