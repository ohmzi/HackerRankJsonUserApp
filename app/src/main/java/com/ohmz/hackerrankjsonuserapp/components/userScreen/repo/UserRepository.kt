package com.ohmz.hackerrankjsonuserapp.components.userScreen.repo

import com.ohmz.hackerrankjsonuserapp.components.userScreen.data.User
import com.ohmz.hackerrankjsonuserapp.service.jsonplaceholderAPI.RetrofitInstance

class UserRepository {
    suspend fun fetchUsers(): List<User>? {
        val response = RetrofitInstance.api.getUsers()
        return if (response.isSuccessful) response.body() else null
    }
}