package com.example.flashcards_app.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.flashcards_app.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun SignupScreen(navController: NavController){
    var email by remember { mutableStateOf("") }
    var password by remember {mutableStateOf("")}
    val context = LocalContext.current //toast

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.email_icon),
                        contentDescription = "Email Icon"
                    )
                },
                label = {Text(text = "Email", fontFamily = FontFamily(Font(R.font.robotocondensed_regular)))},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.password),
                        contentDescription = "Password Icon"
                    )
                },
                label = {Text(text = "Password", fontFamily = FontFamily(Font(R.font.robotocondensed_regular)))},
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(context, "Signup is successful! Please log in.", Toast.LENGTH_SHORT).show()
                                navController.navigate("login_screen"){
                                    popUpTo("signup_screen"){inclusive = true}
                                }
                            }else{
                                Toast.makeText(context, task.exception?.message ?: "Signup is failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sign Up"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = {
                navController.navigate("login_screen"){
                    popUpTo("signup_screen"){inclusive=true}
                }
            }) {
                Text(text= "Already have an account? Log in")
            }
        }

    }
}