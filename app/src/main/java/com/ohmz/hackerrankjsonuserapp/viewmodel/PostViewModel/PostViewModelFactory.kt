package com.ohmz.hackerrankjsonuserapp.viewmodel.PostViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ohmz.hackerrankjsonuserapp.repository.PostRepository

class PostViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostViewModel(repository) as T
    }
}
