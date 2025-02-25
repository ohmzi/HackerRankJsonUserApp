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

    fun updatePost(updatedPost: Post) {
        _posts.value = _posts.value.map { if (it.id == updatedPost.id) updatedPost else it }
    }

    fun deletePost(postId: Int) {
        _posts.value = _posts.value.filterNot { it.id == postId }
    }

}
