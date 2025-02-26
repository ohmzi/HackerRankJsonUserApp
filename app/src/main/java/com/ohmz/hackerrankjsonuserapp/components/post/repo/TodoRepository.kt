package com.ohmz.hackerrankjsonuserapp.components.post.repo

import com.ohmz.hackerrankjsonuserapp.components.todo.data.Todo
import com.ohmz.hackerrankjsonuserapp.service.jsonplaceholderAPI.RetrofitInstance

class TodoRepository {
    suspend fun fetchTodos(userId: Int): List<Todo>? {
        val response = RetrofitInstance.api.getTodos(userId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun fetchAllTodos(): List<Todo>? {
        val response = RetrofitInstance.api.getAllTodos()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateTodoOnServer(todo: Todo): Todo? {
        val response = RetrofitInstance.api.updateTodo(todo.id, todo)
        return if (response.isSuccessful) response.body() else null
    }
}