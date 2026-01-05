package com.example.flashcards_app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem (
    val route: String,
    val title: String,
    val icon: ImageVector,
    val unselectedIcon: ImageVector
){
    object Dashboard: BottomNavItem("dashboard", "Dashboard", Icons.Filled.Home, Icons.Outlined.Home)
    object Review: BottomNavItem("review", "Review", Icons.Filled.Menu, Icons.Outlined.Menu)
}