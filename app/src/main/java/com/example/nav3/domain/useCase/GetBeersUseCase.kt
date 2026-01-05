package com.example.nav3.domain.useCase

import com.example.nav3.domain.model.Beer
import com.example.nav3.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow

class GetBeersUseCase(val repository: BeerRepository){
    suspend operator fun invoke(page: Int, perPage: Int): Flow<Result<List<Beer>>> {
       return repository.getBeers(page,perPage)
    }

}