package com.example.homework

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConversationView(onBack: () -> Unit) {
    Column {
        Button(
            onClick = onBack,
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Back")
        }

        Conversation(SampleData.conversationSample)
    }
}