package com.example.learning.ui.theme

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [userentity::class, usercontent::class], version = 1)
abstract class DDatabase : RoomDatabase() {


    abstract fun userdao(): Userdao

    companion object {
        @Volatile
        var INSTANCE: DDatabase?=null
        fun getdatabase(context: Context): DDatabase{
            return INSTANCE?:synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DDatabase::class.java,
                    "userdatabase"
                ).build()
                INSTANCE=instance
                instance
            }

        }


    }

}