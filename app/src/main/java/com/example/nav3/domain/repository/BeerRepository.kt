package com.example.nav3.domain.repository

import com.example.nav3.domain.RequestState
import com.example.nav3.domain.model.Beer
import kotlinx.coroutines.flow.Flow

interface BeerRepository {

    suspend fun getBeers(page: Int, perPage: Int): Flow<Result<List<Beer>>>
}

