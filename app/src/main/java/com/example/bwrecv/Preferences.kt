package com.example.bwrecv

import android.content.Context
import android.content.SharedPreferences
import com.example.bwrecv.Constant.Companion.PREFERENCES

class Preferences(context: Context) {

    private var pref: SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)


    fun saveToken(token: String){
        val editor = pref.edit()
        editor.putString("TOEKN", token)
        editor.putBoolean("LOGGEDIN", true)
        editor.apply()
    }

    fun getToken(): String{
        return pref.getString("TOKEN", "").toString()
    }

    fun isLoggedIn():Boolean{
        return pref.getBoolean("LOGGEDIN", false)
    }
}