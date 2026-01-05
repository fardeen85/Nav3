package com.example.nav3.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
@Serializable
sealed interface Route: NavKey{

    @Serializable
    data object MainList: Route

    @Serializable
    data class Detail(val id: String): Route

    @Serializable
    data object Settings: Route, NavKey

    @Serializable
    data object Favourites: Route, NavKey

    @Serializable
    data object ChangeListScreen: Route

}