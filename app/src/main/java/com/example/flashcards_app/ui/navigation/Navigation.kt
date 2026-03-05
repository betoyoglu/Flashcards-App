package com.example.flashcards_app.ui.navigation

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flashcards_app.ui.components.BottomBar
import com.example.flashcards_app.ui.screens.dashboard.DashboardScreen
import com.example.flashcards_app.ui.screens.flashcard.FlashcardScreen
import com.example.flashcards_app.ui.screens.login.LoginScreen
import com.example.flashcards_app.ui.screens.login.SignupScreen
import com.example.flashcards_app.ui.screens.review.ReviewScreen
import com.example.flashcards_app.ui.screens.upload.UploadScreen
import com.example.flashcards_app.ui.theme.darkPurple
import com.example.flashcards_app.ui.theme.gradientBluePurple

@Composable
fun Navigation(){
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBarAndFab = currentRoute == BottomNavItem.Dashboard.route ||
            currentRoute == BottomNavItem.Review.route

    Scaffold(
        bottomBar = {
            if (showBottomBarAndFab) {
                BottomBar(navController = navController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (showBottomBarAndFab) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        navController.navigate(OtherNavs.CREATEDECK)
                    },
                    containerColor = gradientBluePurple,
                    contentColor = Color.White,
                    modifier = Modifier
                        .size(64.dp)
                        .offset(y = 56.dp)
                ) {
                    Icon(Icons.Filled.Create, contentDescription = "Create")
                }
            }
        }
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = OtherNavs.SIGNUPSCREEN,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(OtherNavs.SIGNUPSCREEN){
                SignupScreen(navController = navController)
            }
            composable(OtherNavs.LOGINSCREEN){
                LoginScreen(navController = navController)
            }
            composable(BottomNavItem.Dashboard.route){
                DashboardScreen(navController = navController, viewModel = hiltViewModel())
            }

            composable(BottomNavItem.Review.route){
                ReviewScreen(navController = navController, viewModel = hiltViewModel())
            }
            composable(OtherNavs.CREATEDECK){
                UploadScreen(
                    onBackClick = { navController.popBackStack() },
                    onNavigateToFlashcards = { deckId, deckName ->
                        navController.navigate("${OtherNavs.FLASHCARDSCREEN}/$deckId/$deckName") {
                            popUpTo(BottomNavItem.Dashboard.route)
                        }
                    }
                )
            }

            composable("${ OtherNavs.FLASHCARDSCREEN }/{deckId}/{deckName}",
                arguments = listOf(
                    navArgument("deckId") {type = NavType.IntType},
                    navArgument("deckName") {type = NavType.StringType}
                )
            ){ backStackEntry ->
                val deckId = backStackEntry.arguments?.getInt("deckId") ?: 0
                val deckName = backStackEntry.arguments?.getString("deckName") ?: ""

                FlashcardScreen(
                    navController = navController,
                    viewModel = hiltViewModel(),
                    deckId =deckId,
                    deckName = deckName
                )
            }

            composable(OtherNavs.REVIEWSCREEN){
                ReviewScreen(
                    navController = navController,
                    viewModel = hiltViewModel()
                )
            }
        }
    }
}