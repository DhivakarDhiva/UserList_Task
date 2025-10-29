package com.example.task.data.repository

import com.example.task.data.local.UserDao
import com.example.task.data.model.User
import com.example.task.data.remote.RetrofitClient

class UserRepository(private val dao: UserDao) {

    suspend fun fetchUsers(): List<User>{
        val localUsers = dao.getAllUsers()

        return localUsers.ifEmpty {
            var remoteUsers = RetrofitClient.api.getUsers()
            dao.insertAll(remoteUsers)
            remoteUsers
        }
    }

    suspend fun deleteUser(user: User){
        dao.deleteUser(user)
    }
}