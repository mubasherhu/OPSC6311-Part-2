package com.example.opsc_part2

import HomePage
import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.opsc_part2.ui.theme.OPSC_Part2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // setContent is a function used to define the layout of the activity using Jetpack Compose
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            OPSC_Part2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // NavHost sets up the navigation graph
                    NavHost(navController = navController, startDestination = "Screen_1") {
                        // Calling the LoginScreen composable and passing the NavController
                        composable(route = "Screen_1") {
                            LoginScreen(navController)
                        }
                        composable(route = "Screen_2") {
                            HomePage(navController)
                        }
                    }
                }
            }
        }
    }
}
