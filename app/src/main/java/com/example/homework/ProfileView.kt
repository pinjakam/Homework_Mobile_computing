package com.example.homework

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        viewModel.usernameFlow.collectLatest { username = it }
    }
    LaunchedEffect(Unit) {
        viewModel.imageUriFlow.collectLatest { uri ->
            imageUri = if (uri.isNotEmpty()) Uri.parse(uri) else null
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Saved Username: $username")

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
