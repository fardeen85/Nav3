package com.example.nav3.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import androidx.room.Room
import com.example.nav3.data.local.BeerDatabase
import com.example.nav3.data.remote.BeerApi
import com.example.nav3.data.remote.BeerMediator
import com.example.nav3.data.remote.BeerRepositoryImpl
import com.example.nav3.domain.remote.client
import com.example.nav3.domain.repository.BeerRepository
import com.example.nav3.domain.useCase.GetBeersUseCase
import com.example.nav3.presentation.screens.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(ExperimentalPagingApi::class)
val appmodule = module{

    single { client }

    single { BeerApi(get()) }


    // Room Database
    single {
        Room.databaseBuilder(
            androidApplication(),       // context
            BeerDatabase::class.java, // your database class
            "beer_database"           // database name
        ).build()
    }




    single { BeerRepositoryImpl(get()) } bind BeerRepository::class


    single { GetBeersUseCase(get()) }



    single { get<BeerDatabase>().dao }         // DAO



    // Pager singleton
    single {
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = BeerMediator(
                useCase = get(),
                beerDatabase = get()
            ),
            pagingSourceFactory = {
                get<BeerDatabase>().dao.pagingSource()
            }
        )
    }


    viewModel { MainViewModel(get()) }
}


