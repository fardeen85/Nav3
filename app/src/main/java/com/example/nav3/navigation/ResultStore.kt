package com.example.nav3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable


class ResultStore {

    private val results = mutableMapOf<Any, Any?>()

    @Suppress("UNCHECKED_CAST")
    fun <T> getResult(key: Any):T? = results[key] as? T

    fun <T> setResult(key: Any, result: T){
        results[key] = result
    }

    fun removeResult(key: Any){
        results.remove(key)
    }

    fun clear(){
        results.clear()

    }


    companion object {
        val Saver = Saver<ResultStore, Map<Any, Any?>>(
            save = { it.results.toMap() },
            restore = { ResultStore().apply {
                results.putAll(it)
            } }
        )
    }


}



@Composable
fun rememberResultStore() = rememberSaveable(
    saver = ResultStore.Saver
) {
    ResultStore()
}

