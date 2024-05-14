package com.example.opsc_part2

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.opsc_part2.ui.theme.OPSC_Part2Theme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OPSC_Part2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Call the Composable function here
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginContent() {

    LoginScreen()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OPSC_Part2Theme {
        LoginScreen()
    }
}
