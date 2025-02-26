package com.ohmz.hackerrankjsonuserapp.components.userScreen.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ohmz.hackerrankjsonuserapp.components.userScreen.data.User
import com.ohmz.hackerrankjsonuserapp.components.userScreen.viewModel.UserViewModel

@Composable
fun UserList(
    viewModel: UserViewModel, onUserClick: (User) -> Unit
) {
    val users by viewModel.users.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(users) { user ->
            UserRow(user = user, onUserClick = onUserClick)
        }
    }
}
