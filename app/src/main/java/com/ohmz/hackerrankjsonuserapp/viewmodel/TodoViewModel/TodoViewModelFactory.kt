package com.ohmz.hackerrankjsonuserapp.viewmodel.TodoViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TodoViewModelFactory(private val repository: com.ohmz.hackerrankjsonuserapp.repository.TodoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(repository) as T
    }
}
