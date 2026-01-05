package com.example.nav3.data.remote

import com.example.nav3.domain.model.Beer
import com.example.nav3.domain.remote.client
import com.example.nav3.utils.BaseURL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class BeerApi(client: HttpClient) {


    suspend fun getBeers(page: Int, perPage: Int): Result<List<Beer>> {
        return try {
            val response: HttpResponse =
                client.get(BaseURL+"/beers?page=$page&per_page=$perPage")

            if (response.status.isSuccess()) {
                Result.success(response.body())
            } else {
                val errorBody = response.bodyAsText()
                Result.failure(
                    Exception("Error ${response.status.value}: $errorBody")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}