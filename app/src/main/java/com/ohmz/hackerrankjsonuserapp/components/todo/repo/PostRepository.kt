package com.ohmz.hackerrankjsonuserapp.components.todo.repo

import com.ohmz.hackerrankjsonuserapp.components.post.data.Post
import com.ohmz.hackerrankjsonuserapp.service.jsonplaceholderAPI.RetrofitInstance

class PostRepository {
    suspend fun fetchPosts(userId: Int): List<Post>? {
        val response = RetrofitInstance.api.getPosts(userId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun addPost(post: Post): Post? {
        val response = RetrofitInstance.api.createPost(post)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updatePost(post: Post): Post? {
        val response = RetrofitInstance.api.updatePost(post.id!!, post)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deletePost(postId: Int): Boolean {
        val response = RetrofitInstance.api.deletePost(postId)
        return response.isSuccessful
    }
}
