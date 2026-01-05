package com.example.nav3.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey

@Composable
fun AppNavigationBar(
    selectedkey : NavKey,
    onNavItemClick : (NavKey) -> Unit,
    modifier : Modifier = Modifier
){

    BottomAppBar(
        modifier = modifier
    ) {

        TOP_LEVEL_DESTINATIONS.forEach {  (route, navItem) ->

            NavigationBarItem(
                selected = selectedkey == route,
                onClick = { onNavItemClick(route)},
                    icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = navItem.title
                        )

                    },
                alwaysShowLabel = false,
                label = {
                    Text(text = navItem.title)

                },
            )



        }

    }

}