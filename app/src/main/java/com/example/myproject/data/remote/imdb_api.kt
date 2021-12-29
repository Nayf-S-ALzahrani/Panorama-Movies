package com.example.myproject.data.remote

import com.example.myproject.data.remote.dto.popular.MostPopularMoviesDTO
import com.example.myproject.data.remote.dto.recent_dto.RecentMoviesDTO
import com.example.myproject.data.remote.dto.top250_dto.Top250MoviesDTO
import com.example.myproject.domain.model.trailer.Trailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
const val API_KEY ="k_09ko5ajr"//"k_l826iwT6" - "k_5lbkeqi2"
//https://imdb-api.com/Identity/Account/Manage

interface ImdbApi {
    @GET("/en/API/InTheaters/k_5lbkeqi2")
    suspend fun getRecentMovies(): Response<RecentMoviesDTO>

    @GET("/en/API/YouTubeTrailer/k_5lbkeqi2")
    suspend fun getTrailer(@Path("Id") id: String):Response<Trailer>

    @GET("/en/API/Top250Movies/k_5lbkeqi2")
    suspend fun getTop250Movies():Response<Top250MoviesDTO>

    @GET("/en/API/MostPopularMovies/k_5lbkeqi2")
    suspend fun getMostPopular():Response<MostPopularMoviesDTO>


//    @GET("/en/API/SearchMovie/k_95e4lc0l/{expression}")
//    fun searchMovie(@Path("expression") query: String): Call<SearchMovieResponse>
}

