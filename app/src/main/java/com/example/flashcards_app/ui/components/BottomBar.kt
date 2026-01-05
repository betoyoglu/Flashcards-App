package com.example.flashcards_app.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.flashcards_app.R
import com.example.flashcards_app.ui.navigation.BottomNavItem
import com.example.flashcards_app.ui.theme.darkPurple
import com.example.flashcards_app.ui.theme.purple

@Composable
fun BottomBar(navController: NavController){
    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Review
    )

    //şu an hangi sayfada
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color(0xFFFAF9F6),
        contentColor = Color(0xFFD3D3D3)
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId){ //geri tuşuyla home'a dönsün
                            saveState= true
                        }
                        launchSingleTop = true // aynı butona defalarca basınca sayfa üst üste açılmasın
                        restoreState = true //scoll pozisyonu falan koru
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.icon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title,
                    fontFamily = FontFamily(Font(R.font.googlesans_regular))) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = darkPurple,
                    indicatorColor = purple
                )
            )
        }
    }
}