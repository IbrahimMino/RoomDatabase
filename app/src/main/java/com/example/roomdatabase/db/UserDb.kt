package com.example.roomdatabase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabase.dao.MyDao
import com.example.roomdatabase.entity.User

@Database(entities = [User::class], version = 1)
abstract class UserDb :RoomDatabase(){
    abstract fun myDao():MyDao

    companion object{
        @Volatile
        private var INSTANCE: UserDb? = null

        fun getInstance(context:Context) : UserDb{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(context, UserDb::class.java,"user_db")
                        .fallbackToDestructiveMigration()//eskisini ochirib orniga yangi db yozadi
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }


}