package com.example.task.data.remote

import com.example.task.data.model.User
import retrofit2.http.GET

interface ApiService {
@GET("users")
suspend fun getUsers(): List<User>

}