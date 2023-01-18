package com.example.bwrecv.api

import com.example.bwrecv.Constant.Companion.TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Authorization", "Bearer $TOKEN")
            .addHeader("time-zone", "Asia/Kolkata")
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(request)
    }
}