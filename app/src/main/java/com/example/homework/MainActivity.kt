package com.example.homework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homework.ui.theme.HomeworkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeworkTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Conversation : Screen("conversation")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    Surface {
        NavHost(
            navController = navController,
            startDestination = Screen.Main.route
        ) {
            composable(Screen.Main.route) {
                MainView(
                    onNavigateToConversation = {
                        navController.navigate(Screen.Conversation.route) {
                            launchSingleTop = true
                            popUpTo(Screen.Main.route)
                        }
                    }
                )
            }

            composable(Screen.Conversation.route) {
                ConversationView(
                    onBack = {
                        navController.popBackStack(Screen.Main.route, inclusive = false)
                    }
                )
            }
        }
    }
}