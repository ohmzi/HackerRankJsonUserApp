package com.ohmz.hackerrankjsonuserapp.ui.UserScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ohmz.hackerrankjsonuserapp.R
import com.ohmz.hackerrankjsonuserapp.data.Post
import com.ohmz.hackerrankjsonuserapp.data.Todo
import com.ohmz.hackerrankjsonuserapp.data.User
import com.ohmz.hackerrankjsonuserapp.openAI.getGenderForName
import com.ohmz.hackerrankjsonuserapp.viewmodel.PostViewModel.PostViewModel
import com.ohmz.hackerrankjsonuserapp.viewmodel.TodoViewModel.TodoViewModel

enum class SortOrder { ASCENDING, DESCENDING }
const val OPENAI_API_KEY = "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"

@Composable
fun UserDetailScreen(
    user: User,
    postViewModel: PostViewModel,
    todoViewModel: TodoViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(user.id) { postViewModel.getPosts(user.id) }
    var gender by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(user.name) {
        gender = getGenderForName(user.name, OPENAI_API_KEY)
    }
    val avatarResource = when (gender) {
        "male" -> R.drawable.male
        "female" -> R.drawable.female
        else -> R.drawable.ic_launcher_foreground
    }
    val posts by postViewModel.posts.collectAsState()
    val todos by todoViewModel.todos.collectAsState()
    var sortOrder by remember { mutableStateOf(SortOrder.ASCENDING) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val sortedPosts = when (sortOrder) {
        SortOrder.ASCENDING -> posts.sortedBy { it.title }
        SortOrder.DESCENDING -> posts.sortedByDescending { it.title }
    }
    var editingPost by remember { mutableStateOf<Post?>(null) }
    var deletingPost by remember { mutableStateOf<Post?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFF8E2DE2), Color(0xFF4A00E0)
                            )
                        )
                    )
            ) {
                IconButton(
                    onClick = onBack, modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val avatarPainter: Painter = painterResource(id = avatarResource)
                    Image(
                        painter = avatarPainter,
                        contentDescription = "User Avatar",
                        modifier = Modifier.size(80.dp).clip(CircleShape).border(2.dp, Color.White, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                    Text(
                        text = "@${user.username}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = user.phone,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Posts") })
                Tab(selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Todo") })
            }
            when (selectedTabIndex) {
                0 -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp)
                            ) {
                                item {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Posts",
                                            style = MaterialTheme.typography.headlineMedium,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )
                                        Button(onClick = {
                                            sortOrder =
                                                if (sortOrder == SortOrder.ASCENDING) SortOrder.DESCENDING else SortOrder.ASCENDING
                                        }) {
                                            Text(text = if (sortOrder == SortOrder.ASCENDING) "A-Z" else "Z-A")
                                        }
                                    }
                                }
                                items(sortedPosts) { post ->
                                    PostCard(post = post,
                                        onEdit = { editingPost = it },
                                        onDelete = { deletingPost = it })
                                }
                                item { Spacer(modifier = Modifier.height(16.dp)) }
                            }
                        }

                    }
                }

                1 -> {
                    LaunchedEffect(user.id) { todoViewModel.getTodos(user.id) }
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(todos) { todo ->
                            TodoItem(todo = todo,
                                onToggle = { todoId -> todoViewModel.toggleTodoCompletion(todoId) })
                        }
                    }
                }
            }
        }
    }
    if (editingPost != null) {
        var title by remember { mutableStateOf(editingPost!!.title) }
        var body by remember { mutableStateOf(editingPost!!.body) }
        AlertDialog(onDismissRequest = { editingPost = null },
            title = { Text("Edit Post") },
            text = {
                Column {
                    OutlinedTextField(value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") })
                    OutlinedTextField(value = body,
                        onValueChange = { body = it },
                        label = { Text("Body") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    val updatedPost = editingPost!!.copy(title = title, body = body)
                    postViewModel.updatePost(updatedPost)
                    editingPost = null
                }) { Text("Save") }
            },
            dismissButton = {
                Button(onClick = { editingPost = null }) { Text("Cancel") }
            })
    }
    if (deletingPost != null) {
        AlertDialog(onDismissRequest = { deletingPost = null },
            title = { Text("Delete Post") },
            text = { Text("Are you sure you want to delete this post?") },
            confirmButton = {
                Button(onClick = {
                    postViewModel.deletePost(deletingPost!!.id)
                    deletingPost = null
                }) { Text("Delete") }
            },
            dismissButton = {
                Button(onClick = { deletingPost = null }) { Text("Cancel") }
            })
    }
}

@Composable
fun PostCard(post: Post, onEdit: (Post) -> Unit, onDelete: (Post) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp), elevation = cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = post.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = post.body, style = MaterialTheme.typography.bodyMedium)
            }
            PostOptionsMenu(post = post, onEdit = onEdit, onDelete = onDelete)
        }
    }
}

@Composable
fun PostOptionsMenu(post: Post, onEdit: (Post) -> Unit, onDelete: (Post) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Options")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Edit") }, onClick = {
                expanded = false
                onEdit(post)
            })
            DropdownMenuItem(text = { Text("Delete") }, onClick = {
                expanded = false
                onDelete(post)
            })
        }
    }
}

@Composable
fun TodoItem(todo: Todo, onToggle: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp), elevation = cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Checkbox(
                checked = todo.completed,
                onCheckedChange = { onToggle(todo.id) })
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = todo.title)
        }
    }
}
