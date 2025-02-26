@file:OptIn(ExperimentalMaterial3Api::class)

package com.ohmz.hackerrankjsonuserapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ohmz.hackerrankjsonuserapp.components.post.repo.TodoRepository
import com.ohmz.hackerrankjsonuserapp.components.post.viewModel.PostViewModel
import com.ohmz.hackerrankjsonuserapp.components.post.viewModel.PostViewModelFactory
import com.ohmz.hackerrankjsonuserapp.components.todo.repo.PostRepository
import com.ohmz.hackerrankjsonuserapp.components.todo.todoViewModel.TodoViewModel
import com.ohmz.hackerrankjsonuserapp.components.todo.todoViewModel.TodoViewModelFactory
import com.ohmz.hackerrankjsonuserapp.components.todo.ui.AllTodosScreen
import com.ohmz.hackerrankjsonuserapp.components.userScreen.data.User
import com.ohmz.hackerrankjsonuserapp.components.userScreen.repo.UserRepository
import com.ohmz.hackerrankjsonuserapp.components.userScreen.ui.UserDetailScreen
import com.ohmz.hackerrankjsonuserapp.components.userScreen.ui.UserList
import com.ohmz.hackerrankjsonuserapp.components.userScreen.ui.UserRow
import com.ohmz.hackerrankjsonuserapp.components.userScreen.viewModel.UserViewModel
import com.ohmz.hackerrankjsonuserapp.components.userScreen.viewModel.UserViewModelFactory

enum class MainScreenOption {
    Home, AllUsers, AllTodos, SearchUsers, UserDetail
}

@Composable
fun MainScreen() {
    val userRepository = UserRepository()
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userRepository))
    val todoRepository = TodoRepository()
    val todoViewModel: TodoViewModel = viewModel(factory = TodoViewModelFactory(todoRepository))
    val postRepository = PostRepository()
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(postRepository))

    var currentScreen by remember { mutableStateOf(MainScreenOption.Home) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    when (currentScreen) {
        MainScreenOption.Home -> {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        userViewModel.getUsers()
                        currentScreen = MainScreenOption.AllUsers
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Show All Users")
                }
                Button(
                    onClick = {
                        currentScreen = MainScreenOption.AllTodos
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Show Todo")
                }
                Button(
                    onClick = { currentScreen = MainScreenOption.SearchUsers },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Search Users by Name")
                }
            }
        }

        MainScreenOption.AllUsers -> {
            Scaffold(topBar = {
                TopAppBar(title = { Text("All Users") }, navigationIcon = {
                    IconButton(onClick = { currentScreen = MainScreenOption.Home }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                        )
                    }
                })
            }) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    UserList(viewModel = userViewModel, onUserClick = { user ->
                        selectedUser = user
                        currentScreen = MainScreenOption.UserDetail
                    })
                }
            }
        }

        MainScreenOption.AllTodos -> {
            Scaffold(topBar = {
                TopAppBar(title = { Text("All Todos") }, navigationIcon = {
                    IconButton(onClick = { currentScreen = MainScreenOption.Home }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                        )
                    }
                })
            }) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) { AllTodosScreen(todoViewModel = todoViewModel) }
            }
        }

        MainScreenOption.SearchUsers -> {
            Scaffold(topBar = {
                TopAppBar(title = { Text("Search Users") }, navigationIcon = {
                    IconButton(onClick = { currentScreen = MainScreenOption.Home }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                        )
                    }
                })
            }) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search by name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    val users by userViewModel.users.collectAsState()
                    val filteredUsers =
                        users.filter { it.name.contains(searchQuery, ignoreCase = true) }
                    LazyColumn {
                        items(filteredUsers) { user ->
                            UserRow(user = user, onUserClick = {
                                selectedUser = user
                                currentScreen = MainScreenOption.UserDetail
                            })
                        }
                    }
                }
            }
        }

        MainScreenOption.UserDetail -> {
            UserDetailScreen(user = selectedUser!!,
                postViewModel = postViewModel,
                todoViewModel = todoViewModel,
                onBack = {
                    selectedUser = null
                    currentScreen = MainScreenOption.AllUsers
                })
        }
    }
}
