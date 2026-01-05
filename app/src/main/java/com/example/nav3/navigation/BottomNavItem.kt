package com.example.nav3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val icon: ImageVector,
    val title: String,
)

val TOP_LEVEL_DESTINATIONS = mapOf(

    Route.MainList to BottomNavItem(
        icon = Icons.Default.Home,
        title = "Home"
    ),


    Route.Favourites to BottomNavItem(
        icon = Icons.Default.Favorite,
        title = "Favourites"

    ),

    Route.Settings to BottomNavItem(
        icon = Icons.Default.Settings,
        title = "Settings"
    )


    )
