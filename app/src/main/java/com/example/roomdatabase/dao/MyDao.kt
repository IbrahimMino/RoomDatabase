package com.example.roomdatabase.dao

import androidx.room.*
import com.example.roomdatabase.entity.User

@Dao
interface MyDao {
    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM Users")
    fun getAllUsers():List<User>


}