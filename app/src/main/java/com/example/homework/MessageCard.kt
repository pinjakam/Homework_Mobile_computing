package com.example.homework

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun MessageCard(
    msg: Message,
    username: String = "",
    profileImage: Uri? = null
) {
    Row(modifier = Modifier.padding(all = 8.dp)) {

        // Profile picture (if user selected one)
        if (profileImage != null) {
            Image(
                painter = rememberAsyncImagePainter(profileImage),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
        } else {
            // Original placeholder circle
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary
            ) {}
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            // Use saved username if available, otherwise original author
            Text(
                text = if (username.isNotEmpty()) username else msg.author,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 1.dp
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
