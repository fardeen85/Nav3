package com.example.nav3.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.nav3.data.local.BeerDatabase
import com.example.nav3.data.local.BeerEntity
import com.example.nav3.data.mapper.toBeerEntity
import com.example.nav3.domain.model.Beer
import com.example.nav3.domain.useCase.GetBeersUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class BeerMediator(
    private val useCase: GetBeersUseCase,
    private val beerDatabase: BeerDatabase
) : RemoteMediator<Int, BeerEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {

        return try{
            val loadkey = when(loadType){
                LoadType.APPEND -> {

                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null){
                        1
                    }
                    else{
                        ((lastItem.id)/state.config.pageSize)+1
                    }



                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                LoadType.REFRESH -> {

                    1
                }

            }

            delay(2000L)
            val beers = fetchBeers(
                page = loadkey,
                pageSize = state.config.pageSize
            )

            beerDatabase.withTransaction {
                if(loadType == LoadType.REFRESH){
                    beerDatabase.dao.clearAll()
                }
                val beerEntities = beers.map { it.toBeerEntity() }
                beerDatabase.dao.upsertAll(beerEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = beers.isEmpty()
            )

        }
        catch (e: Exception){
            MediatorResult.Error(e)
        }
    }

    private suspend fun fetchBeers(
        page: Int,
        pageSize: Int
    ): List<Beer> {
        return useCase(
            page = page,
            perPage = pageSize
        ).first().getOrElse {
            throw it
        }
    }

}
