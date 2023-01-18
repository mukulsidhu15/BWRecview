package com.example.bwrecv.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface AppDoa {

    @Insert
    suspend fun insertPriceList(priceList: PriceListData)

    @Query("SELECT * FROM priceListInfo ORDER BY rowId DESC")
    fun getList(): LiveData<List<PriceListData>>




}