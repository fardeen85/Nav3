package com.example.nav3.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.nav3.scene.ListDetailScene
import com.example.nav3.scene.rememberListDetailSceneStrategy
import com.example.nav3.presentation.screens.ChangeSettingScreenRoot
import com.example.nav3.presentation.screens.DetailScreenRoot
import com.example.nav3.presentation.screens.FavouritesScreenRoot
import com.example.nav3.presentation.screens.main.MainListScreenRoot
import com.example.nav3.presentation.screens.SettingsScreenRoot

@Composable
fun NavigationRoot(){

    val navigationState = rememberNavigationState(
        startRoute =  Route.MainList,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )

    val navigator = remember {
        Navigator(navigationState)
    }

    val resultStore = rememberResultStore()

    val listState = rememberLazyListState()

    val bottomBarVisible by remember {
        derivedStateOf {
            listState.isScrollInProgress.not()
        }
    }




    Scaffold(

        contentWindowInsets = WindowInsets.safeDrawing.only(
            WindowInsetsSides.Horizontal
        ),
        bottomBar = {
            AnimatedVisibility(
                visible = rememberBottomBarVisibility(listState).value,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 220,
                        easing = FastOutSlowInEasing
                    )
                ) + slideInVertically(
                    animationSpec = tween(
                        durationMillis = 220,
                        easing = FastOutSlowInEasing
                    ),
                    initialOffsetY = { it / 2 } // 👈 subtle movement
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 180,
                        easing = FastOutLinearInEasing
                    )
                ) + slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 180,
                        easing = FastOutLinearInEasing
                    ),
                    targetOffsetY = { it / 2 }
                )

            ) {

                AppNavigationBar(
                    selectedkey = navigationState.topLevelRoutes,
                    onNavItemClick = {
                        navigator.navigate(it)
                    }
                )
            }
        }
    ) { innerPadding->


        NavDisplay(
            onBack = navigator::goBack,
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            sceneStrategy = rememberListDetailSceneStrategy(),
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith
                        slideOutHorizontally { -it }+ fadeOut()
            },
            predictivePopTransitionSpec = {
                slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it }+ fadeOut()
            },
            popTransitionSpec = {
                slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it }+ fadeOut()
            },
            entries = navigationState.toEntries(
                entryProvider {


                    entry<Route.MainList>(
                        metadata = ListDetailScene.listPane()
                    ){
                        MainListScreenRoot(

                            listState,
                            innerPadding,
                            onNext = {navigator.navigate(Route.Detail(it))}
                        )
                    }
                    entry<Route.Favourites> {
                        FavouritesScreenRoot()
                    }

                    entry<Route.Settings> {
                        SettingsScreenRoot(resultStore){
                            navigator.navigate(Route.ChangeListScreen) }
                    }

                    entry<Route.Detail>(
                        metadata = ListDetailScene.detailPane()
                    ) {
                        DetailScreenRoot(it.id)
                    }

                    entry<Route.ChangeListScreen>(
                    ) {
                        ChangeSettingScreenRoot(resultStore){

                            navigator.goBack()
                        }
                    }
                }

            )
        )

    }




}

@Composable
fun rememberBottomBarVisibility(
    listState: LazyListState
): MutableState<Boolean> {
    val isVisible = remember { mutableStateOf(true) }

    var lastIndex by remember { mutableIntStateOf(0) }
    var lastScrollOffset by remember { mutableIntStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex to
                    listState.firstVisibleItemScrollOffset
        }.collect { (index, offset) ->

            val scrollingDown =
                index > lastIndex ||
                        (index == lastIndex && offset > lastScrollOffset)

            val scrollingUp =
                index < lastIndex ||
                        (index == lastIndex && offset < lastScrollOffset)

            if (scrollingDown) {
                isVisible.value = false
            } else if (scrollingUp) {
                isVisible.value = true
            }

            lastIndex = index
            lastScrollOffset = offset
        }
    }

    return isVisible
}

