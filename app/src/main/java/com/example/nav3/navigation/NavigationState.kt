package com.example.nav3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

class NavigationState(

    val startRoute: NavKey, //Main List
    topLevelRoutes: MutableState<NavKey>, //MainList , settings, favourites
    val backStack: Map<NavKey, NavBackStack<NavKey>>  //topDestination + ownBackstack
){

    var topLevelRoutes by topLevelRoutes
    val stackInUse : List<NavKey>
        get() = if(topLevelRoutes == startRoute) {
            listOf(startRoute) //Main list
        }
        else {
            listOf(startRoute, topLevelRoutes) //MainList, //topDestination + ownBackstack
        }



}


@Composable
fun rememberNavigationState(
    startRoute: NavKey,
    topLevelRoutes: Set<NavKey>

): NavigationState {
    val topLevelRoute = rememberSerializable(
        startRoute,
        topLevelRoutes,
        serializer = MutableStateSerializer(PolymorphicSerializer(NavKey::class)),
        configuration = serializersConfig
    ){
        mutableStateOf(startRoute)

    }
    val backStacks = topLevelRoutes.associateWith {key->
        rememberNavBackStack(configuration = serializersConfig,key)
    }



    return remember(startRoute, topLevelRoute){

        NavigationState(
            startRoute,
            topLevelRoutes=topLevelRoute,
            backStack = backStacks,

        )
    }

    }

val serializersConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.MainList::class, Route.MainList.serializer())
            subclass(Route.Detail::class, Route.Detail.serializer())
            subclass(Route.Settings::class, Route.Settings.serializer())
            subclass(Route.Favourites::class, Route.Favourites.serializer())
        }
    }
}

@Composable
fun NavigationState.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>,

): SnapshotStateList<NavEntry<NavKey>>{

    val decoratedEntries =backStack.mapValues {(_,stack)->
        val decorators = listOf(

            rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
            rememberViewModelStoreNavEntryDecorator()

        )

        rememberDecoratedNavEntries(

            backStack = stack,
            entryProvider = entryProvider,
            entryDecorators = decorators

        )
    }

    return  stackInUse.flatMap { decoratedEntries[it]?: emptyList() }
        .toMutableStateList()

}

