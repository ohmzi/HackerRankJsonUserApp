package com.ohmz.hackerrankjsonuserapp.viewmodel.TodoViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohmz.hackerrankjsonuserapp.data.Todo
import com.ohmz.hackerrankjsonuserapp.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos = _todos.asStateFlow()

    fun getTodos(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchTodos(userId)
            _todos.value = result ?: emptyList()
        }
    }

    fun getAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchAllTodos()
            _todos.value = result ?: emptyList()
        }
    }

    fun toggleTodoCompletion(todoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTodos = _todos.value
            val targetTodo = currentTodos.find { it.id == todoId } ?: return@launch
            val updatedTodo = targetTodo.copy(completed = !targetTodo.completed)
            val result = repository.updateTodoOnServer(updatedTodo)
            if (result != null) {
                _todos.value = currentTodos.map { if (it.id == todoId) result else it }
            }
        }
    }

}

