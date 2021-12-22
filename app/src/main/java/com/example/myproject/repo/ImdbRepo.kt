package com.example.myproject.repo

import android.util.Log
import com.example.myproject.api.ImdbApi
import com.example.myproject.models.theater_movies.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG1 = "Theaters"
private const val TAG2 = "Top250Movies"

private const val Base_URL = "https://www.imdb-api.com"

open class ImdbRepo {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Base_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbApi: ImdbApi = retrofit.create(ImdbApi::class.java)

    val getDataTheatersRepo: Flow<List<Item>> = flow {
        val response = imdbApi.getTheatersMovies()
        if (response.isSuccessful) {
            Log.d(TAG1, "the ${response.body()?.items}")
            response.body()?.items?.let {
                emit(it.filter { theater ->
                    theater.image.isNotBlank()
                })
            }
        } else {
            Log.d(TAG1, "the error ${response.errorBody()}")
        }
    }.flowOn(Dispatchers.IO)


    val getDataTop250MoviesRepo: Flow<List<com.example.myproject.models.top250movies.Item>> = flow {
        val response = imdbApi.getTop250Movies()
        if (response.isSuccessful) {
            Log.d(TAG2, "the ${response.body()?.items}")
            response.body()?.items?.let {
                emit(it.filter {
                    it.image.isNotBlank()
                })
            }
        } else {
            Log.d(TAG2, "the error ${response.body()?.errorMessage}")
        }
    }.flowOn(Dispatchers.IO)
}


