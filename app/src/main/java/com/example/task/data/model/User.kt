package com.example.task.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val id:Int,
    val name : String,
    val company: String,
    val username: String,
    val email: String,
    val address: String,
    val zip: String,
    val state: String,
    val country: String,
    val phone: String,
    val photo: String,

)
