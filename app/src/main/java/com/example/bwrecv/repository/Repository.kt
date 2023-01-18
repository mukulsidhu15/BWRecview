package com.example.bwrecv.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bwrecv.api.RetrofitInstance
import com.example.bwrecv.model.login.LoginResponse
import com.example.bwrecv.model.login.OtpResponse
import com.example.bwrecv.model.login.PriceList
import com.example.bwrecv.roomdatabase.AppDoa
import com.example.bwrecv.roomdatabase.PriceListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val appDao: AppDoa) {

    private val loginLiveData = MutableLiveData<LoginResponse>()
    private val otpLiveData = MutableLiveData<OtpResponse>()
    private val priceListLiveData = MutableLiveData<PriceList>()

    val dbList : LiveData<List<PriceListData>> = appDao.getList()

    val login : LiveData<LoginResponse>
    get() = loginLiveData

    val otp: LiveData<OtpResponse>
    get() = otpLiveData

    val priceList: LiveData<PriceList>
    get() = priceListLiveData

    fun login(email: String){
       val result = RetrofitInstance.api.login(email)
        result.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    loginLiveData.postValue(response.body())
                }else{
                    //todo
                    Log.d("checkRes", response.code().toString())
                    loginLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
             //  loginLiveData.postValue(call.clone().execute().body())
            }

        })
    }

    fun otpCode(code: Int, email: String){
        val result = RetrofitInstance.api.otpCode(code,email)
        result.enqueue(object : Callback<OtpResponse>{
            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                if (response.isSuccessful){
                    otpLiveData.postValue(response.body())
                }else{
                    Log.d("checkRes", response.code().toString())
                    otpLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
             //   otpLiveData.postValue(call.clone().execute().body())
            }

        })
    }

    fun getList(){
        val result = RetrofitInstance.api.getList()
        result.enqueue(object : Callback<PriceList>{
            override fun onResponse(call: Call<PriceList>, response: Response<PriceList>) {
                if (response.isSuccessful){
                    priceListLiveData.postValue(response.body())
                }else{
                    priceListLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PriceList>, t: Throwable) {
             //  priceListLiveData.postValue(call.clone().execute().body())
            }
        })
    }

    suspend fun saveList(){
        withContext(Dispatchers.IO){
            val list   = PriceListData(0,priceListLiveData.value!!.data)
            appDao.insertPriceList(list)
        }

    }

}