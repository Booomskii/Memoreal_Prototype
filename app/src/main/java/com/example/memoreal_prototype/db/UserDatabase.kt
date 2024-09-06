package com.example.memoreal_prototype.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class MemorealDatabase : RoomDatabase() {

    abstract fun userDao():UserDao
    companion object{
        @Volatile
        private var INSTANCE : MemorealDatabase? = null
        fun getInstance(context: Context):MemorealDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MemorealDatabase::class.java,
                        "memoreal_database"
                    ).build()
                }
                return instance
            }
        }
    }
}