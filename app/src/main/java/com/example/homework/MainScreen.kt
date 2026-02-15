package com.example.homework

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay

@Composable
fun MainScreen(activity: MainActivity, viewModel: ProfileViewModel, modifier: Modifier = Modifier) {

    val movement = remember { mutableStateOf(0f) }
    var username by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        viewModel.usernameFlow.collect { username = it }
    }
    LaunchedEffect(Unit) {
        viewModel.imageUriFlow.collect { uri ->
            imageUri = if (uri.isNotEmpty()) Uri.parse(uri) else null
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            movement.value = activity.movementValue
            delay(200)
        }
    }

    Column(modifier = modifier.padding(20.dp)) {

        // Profile card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Hello $username! Let's make this day active :)",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Movement card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Movement", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text(
                    text = String.format("%.2f", movement.value),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(8.dp))
                Text("You will receive a notification if you're inactive for 30 minutes.")
            }
        }
    }
}
