package com.ohmz.hackerrankjsonuserapp.viewmodel.PostViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohmz.hackerrankjsonuserapp.data.Post
import com.ohmz.hackerrankjsonuserapp.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    fun getPosts(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchPosts(userId)
            _posts.value = result ?: emptyList()
        }
    }

    fun addPost(newPost: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            val createdPost = repository.addPost(newPost)
            if (createdPost != null) {
                _posts.value += createdPost
            }
        }
    }

    fun updatePost(updatedPost: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updatePost(updatedPost)
            if (result != null) {
                _posts.value = _posts.value.map { if (it.id == result.id) result else it }
            }
        }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = repository.deletePost(postId)
            if (success) {
                _posts.value = _posts.value.filterNot { it.id == postId }
            }
        }
    }
}
