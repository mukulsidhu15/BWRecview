package com.example.bwrecv.model.login

data class LoginResponse(
    val `data`: Data,
    val message: String,
    var statusCode: Int
)