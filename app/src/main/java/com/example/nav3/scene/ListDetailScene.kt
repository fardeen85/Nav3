package com.example.nav3.scene

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene

class ListDetailScene <T: Any>(
    val list: NavEntry<T>,
    val detail: NavEntry<T>,
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,

): Scene<T>{

    override val entries: List<NavEntry<T>>
        get() = listOf(list, detail)



    override val content: @Composable (() -> Unit)={

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.weight(4f)
            ) {

                list.Content()
            }

            Column(
                modifier = Modifier.weight(6f)
            ) {

                detail.Content()
            }

        }
    }


    companion object{

        const val List_KEY = "ListDetailScene-List"
        const val Detail_KEY = "ListDetailScene-Detail"

        fun listPane() = mapOf(List_KEY to true)
        fun detailPane() = mapOf(Detail_KEY to true)



    }


}