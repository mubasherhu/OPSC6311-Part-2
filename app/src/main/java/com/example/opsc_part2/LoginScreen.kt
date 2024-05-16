import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showAlertDialog by remember { mutableStateOf(false) }
    var showSuccessAlertDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontSize = 45.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Login into your Account", fontSize = 15.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email Address") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.endsWith("@gmail.com") ){
                    if( password.length >= 4){
                        alertMessage = "Welcome"
                        showSuccessAlertDialog = true
                        navController.navigate(route = "Screen_2")
                        coroutineScope.launch {
                            delay(4000)
                            showSuccessAlertDialog = false
                        }
                        email = ""
                        password = ""
                    }else{
                        alertMessage = "Password must be longer than 4 characters"
                        showAlertDialog = true
                        coroutineScope.launch {
                            delay(2500) // Delay for 2 seconds
                            showAlertDialog = false // Hide AlertDialog after delay
                        }
                    }


                }else{
                    alertMessage = "Incorrect email or password"
                    showAlertDialog = true
                    coroutineScope.launch {
                        delay(2500) // Delay for 2 seconds
                        showAlertDialog = false // Hide AlertDialog after delay
                    }
                }

            }
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Forgot Password?")

//        LaunchedEffect(showToast) {
//            if (showToast) {
//                delay(2000)
//                showToast = false
//            }
//        }
//
//        if (showToast) {
//            Toast(message = "Login Successful")
//        }

        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                title = { Text(text = "Login Error") },
                text = { Text(alertMessage) },
                confirmButton = {}
            )
        }

        if (showSuccessAlertDialog){
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                title = { Text(text = "Login Success") },
                text = { Text(alertMessage) },
                confirmButton = {}
            )
        }
    }
}

@Composable
fun Toast(message: String) {
    Text(text = message)
}