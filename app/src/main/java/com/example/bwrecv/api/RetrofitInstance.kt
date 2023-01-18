package com.example.bwrecv.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://www.cellphonesrecycling.com/bwRecycling/public/api/"
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor())
    }.build()

    private val retrofit by lazy {
        val gson = GsonBuilder().setLenient().create()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}