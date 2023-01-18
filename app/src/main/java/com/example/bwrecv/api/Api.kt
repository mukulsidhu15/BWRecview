package com.example.bwrecv.api

import com.example.bwrecv.model.login.LoginResponse
import com.example.bwrecv.model.login.OtpResponse
import com.example.bwrecv.model.login.PriceList
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("CheckStatus")
    fun otpCode(
        @Field("Onetimecode") otp:Int,
        @Field("email") email: String
    ):Call<OtpResponse>

    @GET("getPrice")
    fun getList():Call<PriceList>

}