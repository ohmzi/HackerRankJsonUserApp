package com.ohmz.hackerrankjsonuserapp.components.todo.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ohmz.hackerrankjsonuserapp.components.todo.data.Todo
import com.ohmz.hackerrankjsonuserapp.components.todo.todoViewModel.TodoViewModel
import com.ohmz.hackerrankjsonuserapp.util.showToast


@Composable
fun TodoItem(todo: Todo, onToggle: (Int) -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp), elevation = cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = todo.completed, onCheckedChange = {
                onToggle(todo.id)
                context.showToast("Data will not be updated on the server but it will be faked.")
            })
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = todo.title)
        }
    }
}

@Composable
fun AllTodosScreen(todoViewModel: TodoViewModel) {
    LaunchedEffect(Unit) { todoViewModel.getAllTodos() }
    val todos by todoViewModel.todos.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(todos) { todo ->
            TodoItem(todo = todo, onToggle = { todoId ->
                todoViewModel.toggleTodoCompletion(todoId)
            })
        }
    }
}



