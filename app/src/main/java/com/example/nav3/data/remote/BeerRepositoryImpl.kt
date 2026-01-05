package com.example.nav3.data.remote

import com.example.nav3.domain.RequestState
import com.example.nav3.domain.model.Beer
import com.example.nav3.domain.repository.BeerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class BeerRepositoryImpl(
    private val api: BeerApi
) : BeerRepository {

    override suspend fun getBeers(page: Int, perPage: Int): Flow<Result<List<Beer>>> = flow {
        emit(api.getBeers(page, perPage))
    }.flowOn(Dispatchers.IO)
}
