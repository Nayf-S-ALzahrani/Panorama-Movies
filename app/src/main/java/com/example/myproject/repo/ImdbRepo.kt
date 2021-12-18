package com.example.myproject.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.myproject.api.ImdbApi
import com.example.myproject.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "ImdbRepo"
private const val Base_URL = "https://www.imdb-api.com"

open class ImdbRepo {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Base_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbApi: ImdbApi = retrofit.create(ImdbApi::class.java)

    val getData: kotlinx.coroutines.flow.Flow<List<Item>> = flow {

        val response = imdbApi.getTheatersMovies()
        if (response.isSuccessful) {
            Log.e(TAG, "the ${response.raw()}")
            response.body()?.items?.let { emit(it) }
        } else {
            Log.e(TAG, "the error ${response.errorBody()}")
        }
    }.flowOn(Dispatchers.IO)


//    suspend fun getTheatersMovies(): LiveData<List<Item>> {
//        return liveData(Dispatchers.IO) {
//            val response = imdbApi.getTheatersMovies()
//            if (response.isSuccessful) {
//                response.body()?.items?.let { emit(it) }
//            } else {
//                Log.e(TAG, "the error is ${response.errorBody()}")
//            }
//        }
    }

//    fun theatersMovies(): LiveData<List<Item>> {
//
//        return liveData(Dispatchers.IO) {
//            val response = imdbApi.theatersMovies()
//            if (response.isSuccessful){
//                response.body()?.items?.let { emit(it) }
//            }else{
//                Log.e(TAG , "the error is ${response.errorBody()}")
//            }
//        }
//
//    }
