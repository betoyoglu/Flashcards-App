package com.example.flashcards_app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flashcards_app.ui.components.BottomBar
import com.example.flashcards_app.ui.screens.dashboard.DashboardScreen
import com.example.flashcards_app.ui.screens.flashcard.FlashcardScreen
import com.example.flashcards_app.ui.screens.review.ReviewScreen
import com.example.flashcards_app.ui.screens.upload.UploadScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        bottomBar = {
            if(currentRoute == BottomNavItem.Dashboard.route || currentRoute == BottomNavItem.Review.route){
                BottomBar(navController= navController)
            }
        },
        floatingActionButton = {
            if(currentRoute == BottomNavItem.Dashboard.route){
                LargeFloatingActionButton(
                    onClick = {navController.navigate("create_deck")},
                    shape = CircleShape
                ) {
                    Icon(Icons.Filled.Create, "add")
                }
            }
        }
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(BottomNavItem.Dashboard.route){
                DashboardScreen(navController = navController, viewModel = hiltViewModel())
            }
            composable(OtherNavs.CREATEDECK){
                UploadScreen(
                    onBackClick = { navController.popBackStack() },
                    onSaveClick = {
                        navController.popBackStack()
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