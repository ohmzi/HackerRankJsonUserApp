package com.ohmz.hackerrankjsonuserapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

enum class Screen { Home, Users, UserDetail }

@Composable
fun MainScreen() {
    val userRepository = UserRepository()
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userRepository))
    val postRepository = PostRepository()
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(postRepository))
    val todoRepository = TodoRepository()
    val todoViewModel: TodoViewModel = viewModel(factory = TodoViewModelFactory(todoRepository))

    var currentScreen by remember { mutableStateOf(Screen.Home) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    when (currentScreen) {
        Screen.Home -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    userViewModel.getUsers()
                    currentScreen = Screen.Users
                }) {
                    Text("Show Users")
                }
            }
        }

        Screen.Users -> {
            UserList(viewModel = userViewModel, onUserClick = { user ->
                selectedUser = user
                currentScreen = Screen.UserDetail
            })
        }

        Screen.UserDetail -> {
            UserDetailScreen(user = selectedUser!!,
                postViewModel = postViewModel,
                todoViewModel = todoViewModel,
                onBack = { currentScreen = Screen.Users })
        }
    }
}
