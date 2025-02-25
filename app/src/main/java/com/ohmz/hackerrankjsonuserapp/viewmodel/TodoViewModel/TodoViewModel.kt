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

    fun toggleTodoCompletion(todoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _todos.value
            val targetTodo = currentList.find { it.id == todoId } ?: return@launch
            val toggled = targetTodo.copy(completed = !targetTodo.completed)
            val updatedFromServer = repository.updateTodoOnServer(toggled)
            val finalTodo = updatedFromServer ?: toggled
            val updatedList = currentList.map { if (it.id == todoId) finalTodo else it }
            _todos.value = updatedList
        }
    }
}

