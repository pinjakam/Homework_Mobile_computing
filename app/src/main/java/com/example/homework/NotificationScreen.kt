package com.example.homework

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotificationScreen(viewModel: ProfileViewModel, modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(16.dp)) {

        Text("Notifications", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(viewModel.notifications) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Text(
                        text = note,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
