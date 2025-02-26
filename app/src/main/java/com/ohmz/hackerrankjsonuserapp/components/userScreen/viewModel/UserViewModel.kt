package com.ohmz.hackerrankjsonuserapp.components.userScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohmz.hackerrankjsonuserapp.components.userScreen.data.User
import com.ohmz.hackerrankjsonuserapp.components.userScreen.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchUsers()
            _users.value = result ?: emptyList()
        }
    }
}
