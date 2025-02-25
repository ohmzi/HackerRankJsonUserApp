package com.ohmz.hackerrankjsonuserapp.repository

import com.ohmz.hackerrankjsonuserapp.data.RetrofitInstance
import com.ohmz.hackerrankjsonuserapp.data.Todo

class TodoRepository {
    suspend fun fetchTodos(userId: Int): List<Todo>? {
        val response = RetrofitInstance.api.getTodos(userId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateTodoOnServer(todo: Todo): Todo? {
        val response = RetrofitInstance.api.updateTodo(todo.id, todo)
        return if (response.isSuccessful) response.body() else null
    }
}