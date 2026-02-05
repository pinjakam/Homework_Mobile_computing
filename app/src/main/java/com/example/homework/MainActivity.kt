package com.example.homework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homework.ui.theme.HomeworkTheme

class MainActivity : ComponentActivity() {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeworkTheme {
                val navController = rememberNavController()

                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = "main"
                    ) {
                        composable("main") {
                            MainView(
                                viewModel = profileViewModel,
                                onNavigateProfile = { navController.navigate("profile") },
                                onNavigateChat = { navController.navigate("chat") }
                            )
                        }

                        composable("profile") {
                            ProfileView(
                                viewModel = profileViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable("chat") {
                            ConversationView(
                                viewModel = profileViewModel,   // ← ADD THIS
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
