package com.example.nav3.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nav3.data.local.BeerEntity
import com.example.nav3.data.mapper.toBeer
import com.example.nav3.domain.useCase.GetBeersUseCase
import kotlinx.coroutines.flow.map

class MainViewModel(
    pager: Pager<Int, BeerEntity>,
) : ViewModel(){


    val beerPagination = pager
        .flow
        .map {
            pagingData ->
            pagingData.map{it.toBeer()}

        }
        .cachedIn(viewModelScope)


}