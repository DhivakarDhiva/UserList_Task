package com.example.task.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.task.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers() : List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: List<User>)

    @Delete
    suspend fun deleteUser(user: User)


}