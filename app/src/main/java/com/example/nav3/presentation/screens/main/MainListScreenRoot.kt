package com.example.nav3.presentation.screens.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nav3.presentation.components.BeerItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainListScreenRoot(
    listState: LazyListState,
    innerPadding: PaddingValues,
    onNext: (id: String) -> Unit,
) {
    val context = LocalContext.current
    val viewmodel = koinViewModel<MainViewModel>()
    val beers = viewmodel.beerPagination.collectAsLazyPagingItems()

    // Show toast on refresh error
    LaunchedEffect(key1 = beers.loadState.refresh) {
        val refreshState = beers.loadState.refresh
        if (refreshState is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: ${refreshState.error.message}",
                Toast.LENGTH_LONG
            ).show()

            Log.d("TAG",refreshState.error.message.toString())
        }
    }

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {

        if (beers.loadState.refresh is LoadState.Loading) {

            LoadingIndicator()

        }
    else{

        LazyColumn(state = listState, modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding)
        {
            item { Spacer(modifier = Modifier.height(30.dp)) }

            item {
                DockedSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { active = false },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text("Search") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                ) {
                    repeat(5){

                        ListItem(
                            headlineContent = { Text("Suggestion $it") },
                            modifier = Modifier.padding(8.dp).clickable{}
                        )
                    }
                }
            }


            // Display beer items from Paging
            items(beers.itemCount){
                index ->
                val beer = beers[index]
                if(beer!=null){
                    BeerItem(beer = beer) {
                        onNext(beer.id.toString())
                    }
                }
            }


            // Optional: show loading at the end
            beers.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val e = loadState.append as LoadState.Error
                        item {
                            Text(
                                text = "Error: ${e.error.message}",
                                color = Color.Red,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
}
