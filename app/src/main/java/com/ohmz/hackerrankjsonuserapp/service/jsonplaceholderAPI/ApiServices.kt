package com.ohmz.hackerrankjsonuserapp.service.jsonplaceholderAPI

import com.ohmz.hackerrankjsonuserapp.components.post.data.Post
import com.ohmz.hackerrankjsonuserapp.components.todo.data.Todo
import com.ohmz.hackerrankjsonuserapp.components.userScreen.data.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("posts")
    suspend fun getPosts(@Query("userId") userId: Int): Response<List<Post>>

    @POST("posts")
    suspend fun createPost(@Body post: Post): Response<Post>

    @PATCH("posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int, @Body updatedPost: Post
    ): Response<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(
        @Path("id") id: Int
    ): Response<Unit>

    @GET("todos")
    suspend fun getTodos(@Query("userId") userId: Int): Response<List<Todo>>

    @GET("todos")
    suspend fun getAllTodos(): Response<List<Todo>>

    @PATCH("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int, @Body todo: Todo
    ): Response<Todo>
}

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }
}