package com.example.bwrecv.roomdatabase

import android.content.Context
import androidx.room.*
import com.example.bwrecv.model.login.DataXX
import com.google.gson.Gson

@Database(entities = [PriceListData::class], version = 1, exportSchema = true)
@TypeConverters(
    ConverterPriceList::class
)
abstract class RoomDb: RoomDatabase() {
    abstract fun dao(): AppDoa?

    companion object {

        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getAppDatabase(context: Context): RoomDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {

                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, RoomDb::class.java, "AppDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}

class ConverterPriceList {
    @TypeConverter
    fun listToJsonString(value: List<DataXX>?):String? = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String?) = Gson().fromJson(value, Array<DataXX>::class.java).toList()
}