package com.example.flashcards_app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flashcards_app.ui.components.BottomBar
import com.example.flashcards_app.ui.screens.dashboard.DashboardScreen
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
            composable(FabNav.CreateDeck){
                UploadScreen(
                    onBackClick = { navController.popBackStack() },
                    onSaveClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}