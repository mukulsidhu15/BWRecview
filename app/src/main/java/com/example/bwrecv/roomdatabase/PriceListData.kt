package com.example.bwrecv.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bwrecv.model.login.DataXX

@Entity(tableName = "priceListInfo")
data class PriceListData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "priceList") val priceList: List<DataXX>
)