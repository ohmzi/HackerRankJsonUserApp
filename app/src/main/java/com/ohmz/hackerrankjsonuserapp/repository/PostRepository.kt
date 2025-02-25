package com.ohmz.hackerrankjsonuserapp.repository

import com.ohmz.hackerrankjsonuserapp.data.Post
import com.ohmz.hackerrankjsonuserapp.data.RetrofitInstance

class PostRepository {
    suspend fun fetchPosts(userId: Int): List<Post>? {
        val response = RetrofitInstance.api.getPosts(userId)
        return if (response.isSuccessful) response.body() else null
    }
}
