package com.ohmz.hackerrankjsonuserapp.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("posts")
    suspend fun getPosts(@Query("userId") userId: Int): Response<List<Post>>

    @GET("todos")
    suspend fun getTodos(@Query("userId") userId: Int): Response<List<Todo>>

    @PATCH("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int, @Body updatedTodo: Todo
    ): Response<Todo>
}


object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }
}