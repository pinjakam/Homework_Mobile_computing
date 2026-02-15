package com.example.homework

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun AppNavigation(activity: MainActivity, viewModel: ProfileViewModel) {

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    label = { Text("Home") },
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    label = { Text("Notifications") },
                    icon = { Icon(Icons.Filled.Notifications, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    label = { Text("Profile") },
                    icon = { Icon(Icons.Filled.Person, contentDescription = null) }
                )
            }
        }
    ) { padding ->

        when (selectedTab) {
            0 -> MainScreen(activity, viewModel, Modifier.padding(padding))
            1 -> NotificationScreen(viewModel, Modifier.padding(padding))
            2 -> ProfileView(viewModel) {}
        }
    }
}
