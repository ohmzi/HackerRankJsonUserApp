package com.ohmz.hackerrankjsonuserapp.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ohmz.hackerrankjsonuserapp.data.User
import com.ohmz.hackerrankjsonuserapp.repository.PostRepository
import com.ohmz.hackerrankjsonuserapp.repository.TodoRepository
import com.ohmz.hackerrankjsonuserapp.repository.UserRepository
import com.ohmz.hackerrankjsonuserapp.ui.UserScreen.UserDetailScreen
import com.ohmz.hackerrankjsonuserapp.ui.UserScreen.UserList
import com.ohmz.hackerrankjsonuserapp.viewmodel.PostViewModel.PostViewModel
import com.ohmz.hackerrankjsonuserapp.viewmodel.PostViewModel.PostViewModelFactory
import com.ohmz.hackerrankjsonuserapp.viewmodel.TodoViewModel.TodoViewModel
import com.ohmz.hackerrankjsonuserapp.viewmodel.TodoViewModel.TodoViewModelFactory
import com.ohmz.hackerrankjsonuserapp.viewmodel.UserViewModel.UserViewModel
import com.ohmz.hackerrankjsonuserapp.viewmodel.UserViewModel.UserViewModelFactory

@Composable
fun MainScreen() {
    val userRepository = UserRepository()
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userRepository))

    val postRepository = PostRepository()
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(postRepository))

    val todoRepository = TodoRepository()
    val todoViewModel: TodoViewModel = viewModel(factory = TodoViewModelFactory(todoRepository))

    var selectedUser by remember { mutableStateOf<User?>(null) }

    if (selectedUser == null) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Button(onClick = { userViewModel.getUsers() }) {
                Text("Show Users")
            }
            UserList(
                viewModel = userViewModel,
                onUserClick = { user -> selectedUser = user }
            )
        }
    } else {
        UserDetailScreen(
            user = selectedUser!!,
            postViewModel = postViewModel,
            todoViewModel = todoViewModel,
            onBack = { selectedUser = null }
        )
    }
}
