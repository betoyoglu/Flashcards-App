package com.example.flashcards_app.ui.screens.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.flashcards_app.BuildConfig
import com.example.flashcards_app.R
import com.example.flashcards_app.ui.theme.gradientBlue
import com.example.flashcards_app.ui.theme.gradientBluePurple
import com.example.flashcards_app.ui.theme.purple
import com.example.flashcards_app.util.movingStripesBackground
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(navController: NavController){
    var email by remember { mutableStateOf("") }
    var password by remember {mutableStateOf("")}
    val context = LocalContext.current //toast
    var forgotPasswordDialogBox by remember {mutableStateOf(false)}
    val webClientId = BuildConfig.webClientId

    val googleSignInOptions = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId) //firebasedeki web client id
            .requestEmail()
            .build()
    }

    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, googleSignInOptions)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(context, "Google Sign-In Successful!", Toast.LENGTH_SHORT).show()
                        navController.navigate("home"){
                            popUpTo("login"){inclusive = true}
                        }
                    }else{
                        Toast.makeText(context, "Google Sign-In Failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }catch (e: Exception){
            Toast.makeText(context, "Google Sign-In Failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .movingStripesBackground(
            stripeColor = gradientBlue,
            backgroundColor = gradientBluePurple
        )
        .padding(24.dp),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "Log In to Continue",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                color = purple
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.email_icon),
                        contentDescription = "Email Icon",
                        tint = Color.White
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                label = {Text(text = "Email", color = purple, fontFamily = FontFamily(Font(R.font.robotocondensed_regular)))},
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
                        contentDescription = "Email Icon",
                        tint = Color.White
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                label = {Text(text = "Password", color = purple, fontFamily = FontFamily(Font(R.font.robotocondensed_regular)))},
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    Firebase.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(context, "Login is successful!", Toast.LENGTH_SHORT).show()
                                navController.navigate("dashboard"){
                                    popUpTo("login_screen"){inclusive = true}
                                }
                            }else{
                                Toast.makeText(context, task.exception?.message ?: "Login is failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purple,
                    contentColor = gradientBluePurple
                )
            ) {
                Text(
                    text = "Log In"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            //google logosu olan buton
            AndroidView(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
                factory = {context ->
                    SignInButton(context).apply {
                        setSize(SignInButton.SIZE_WIDE)
                        setOnClickListener {
                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)
                        }
                    }
                }){
            }

            if(forgotPasswordDialogBox){
                var resetEmail by remember {mutableStateOf("")}
                val context = LocalContext.current

                AlertDialog(
                    title = {Text("Forgot Password")},
                    text = {
                        OutlinedTextField(
                            value = resetEmail,
                            onValueChange = {resetEmail = it},
                            label = {Text("Email")},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if(resetEmail.isNotBlank()){
                                Firebase.auth.sendPasswordResetEmail(resetEmail)
                                    .addOnCompleteListener { task ->
                                        if(task.isSuccessful){
                                            Toast.makeText(context, "Password reset email sent.", Toast.LENGTH_SHORT).show()
                                            forgotPasswordDialogBox = false
                                        }else{
                                            Toast.makeText(context, "Registered email not found.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }else{
                                Toast.makeText(context, "Please enter your registered email.", Toast.LENGTH_SHORT).show()
                            }
                        }) {
                            Text("Send")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {forgotPasswordDialogBox = false}) {
                            Text("Cancel")
                        }
                    },
                    onDismissRequest = { //ekranda başka yerlere tıklarsa
                        forgotPasswordDialogBox = false
                    }
                )
            }
            TextButton(onClick = {forgotPasswordDialogBox = true}, modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Forgot Password?", color = purple)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {
                navController.navigate("signup_screen"){
                    popUpTo("login_screen"){inclusive=true}
                }
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text= "Don't have an account? Sign up.", color = purple)
            }
        }

    }
}