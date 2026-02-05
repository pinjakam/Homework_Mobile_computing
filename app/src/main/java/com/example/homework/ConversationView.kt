package com.example.homework

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ConversationView(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Load saved username
    LaunchedEffect(Unit) {
        viewModel.usernameFlow.collectLatest { username = it }
    }

    // Load saved image URI
    LaunchedEffect(Unit) {
        viewModel.imageUriFlow.collectLatest { uri ->
            imageUri = if (uri.isNotEmpty()) Uri.parse(uri) else null
        }
    }

    Column {
        Button(onClick = onBack) {
            Text("Back")
        }

        LazyColumn {
            items(SampleData.conversationSample) { message ->
                MessageCard(
                    msg = message,
                    username = username,
                    profileImage = imageUri
                )
            }
        }
    }
}
