package com.ohmz.hackerrankjsonuserapp.repository

import com.ohmz.hackerrankjsonuserapp.data.RetrofitInstance
import com.ohmz.hackerrankjsonuserapp.data.User

class UserRepository {
    suspend fun fetchUsers(): List<User>? {
        val response = RetrofitInstance.api.getUsers()
        return if (response.isSuccessful) response.body() else null
    }
}