package com.example.bwrecv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bwrecv.model.login.LoginResponse
import com.example.bwrecv.model.login.OtpResponse
import com.example.bwrecv.model.login.PriceList
import com.example.bwrecv.repository.Repository
import com.example.bwrecv.roomdatabase.PriceListData
import com.example.bwrecv.roomdatabase.RoomDb
import kotlinx.coroutines.launch

class ViewModel(app: Application): AndroidViewModel(app) {

    private val repository: Repository
    val loginResponse: LiveData<LoginResponse>
    get() = repository.login

    val otpResponse: LiveData<OtpResponse>
    get() = repository.otp

    val priceList: LiveData<PriceList>
    get() = repository.priceList

    var dbList: LiveData<List<PriceListData>>

    init {
        val dao = RoomDb.getAppDatabase(getApplication()).dao()
        repository = dao?.let { Repository(it) }!!
        dbList =  repository.dbList
    }

    fun login(email: String){
        viewModelScope.launch {
            repository.login(email)
        }
    }


    fun otpCode(code: Int, email: String){
        viewModelScope.launch {
            repository.otpCode(code,email)
        }
    }

    fun getList(){
        viewModelScope.launch {
            repository.getList()
        }
    }

    fun saveListToDb(){
        viewModelScope.launch {
            repository.saveList()
        }
    }
}