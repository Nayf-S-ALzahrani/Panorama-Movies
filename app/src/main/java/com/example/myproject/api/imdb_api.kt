package com.example.myproject.api

import retrofit2.Response
import retrofit2.http.GET

interface IMDB_API {
    @GET("en/API/InTheaters/k_09ko5ajr")
    suspend fun TheatersMovies(): Response<>
    @GET()
}
