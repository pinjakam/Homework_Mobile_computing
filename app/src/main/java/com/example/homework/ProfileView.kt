package com.example.homework

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current

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

    // Image picker launcher
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {

                // ⭐ Persist permission so the URI never disappears
                try {
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (_: Exception) {
                    // Some devices throw if permission already granted — safe to ignore
                }

                imageUri = uri
            }
        }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Profile image preview
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(16.dp))

        // Pick image button
        Button(
            onClick = {
                imagePickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        ) {
            Text("Pick Profile Image")
        }

        Spacer(Modifier.height(24.dp))

        // Username field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        Spacer(Modifier.height(24.dp))

        // Save button
        Button(
            onClick = {
                viewModel.saveProfile(username, imageUri)
                onBack()
            }
        ) {
            Text("Save")
        }

        Spacer(Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
