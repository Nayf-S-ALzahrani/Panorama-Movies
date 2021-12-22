package com.example.myproject.api

import com.example.myproject.models.theater_movies.TheatersMovies
import com.example.myproject.models.theater_movies.Trailer
import com.example.myproject.models.top250movies.Top250Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
const val API_KEY ="k_09ko5ajr"
//https://imdb-api.com/Identity/Account/Manage

interface ImdbApi {
    @GET("/en/API/InTheaters/k_09ko5ajr")
    suspend fun getTheatersMovies(): Response<TheatersMovies>

    @GET("/en/API/YouTubeTrailer/k_09ko5ajr")
    suspend fun getTrailer(@Path("Id") id: String):Response<Trailer>

    @GET("/en/API/Top250Movies/k_09ko5ajr")
    suspend fun getTop250Movies():Response<Top250Movies>

//    @GET("/en/API/SearchMovie/k_95e4lc0l/{expression}")
//    fun searchMovie(@Path("expression") query: String): Call<SearchMovieResponse>
}

