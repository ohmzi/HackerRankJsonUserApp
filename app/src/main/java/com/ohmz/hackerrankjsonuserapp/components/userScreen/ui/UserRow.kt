package com.ohmz.hackerrankjsonuserapp.components.userScreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ohmz.hackerrankjsonuserapp.components.userScreen.data.User


@Composable
fun UserRow(
    user: User, onUserClick: (User) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onUserClick(user) }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Username: ${user.username}", style = MaterialTheme.typography.headlineMedium
            )
            Text(text = "Name: ${user.name}", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Phone: ${user.phone}", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Address: ${user.address.suite}, ${user.address.street}, ${user.address.city}, ${user.address.zipcode}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Website: ${user.website}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Company: ${user.company.name}", style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Catchphrase: ${user.company.catchPhrase}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = "Business: ${user.company.bs}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
