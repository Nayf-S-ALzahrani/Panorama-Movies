package com.example.myproject.data.remote

import com.example.myproject.data.remote.dto.coming_soon_dto.ComingSoonDTO
import com.example.myproject.data.remote.dto.popular_dto.MostPopularMoviesDTO
import com.example.myproject.data.remote.dto.recent_dto.RecentMoviesDTO
import com.example.myproject.data.remote.dto.detail_dto.ShowDetailDTO
import com.example.myproject.data.remote.dto.top250_dto.Top250MoviesDTO
import com.example.myproject.data.remote.dto.top250_tvs_dto.Top250TVsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val API_KEY ="k_09ko5ajr"//"k_l826iwT6" - "k_5lbkeqi2" - "k_rq0aydb1"- "k_411a6yzf" - "k_0l5yyrm0" - "k_mu2i99lq" - "k_lu62y3po"
//https://imdb-api.com/Identity/Account/Manage

interface ImdbApi {
    @GET("/en/API/InTheaters/k_09ko5ajr")
    suspend fun getRecentMovies(): Response<RecentMoviesDTO>

//    @GET("/en/API/YouTubeTrailer/k_5lbkeqi2")
//    suspend fun getTrailer(@Path("Id") id: String):Response<Trailer>

    @GET("/en/API/Top250Movies/k_09ko5ajr")
    suspend fun getTop250Movies():Response<Top250MoviesDTO>

    @GET("/en/API/MostPopularMovies/k_09ko5ajr")
    suspend fun getMostPopular():Response<MostPopularMoviesDTO>

    @GET("/en/API/ComingSoon/k_09ko5ajr")
    suspend fun getComingSoon():Response<ComingSoonDTO>

    @GET("/en/API/Top250TVs/k_09ko5ajr")
    suspend fun getTop250TVs():Response<Top250TVsDTO>

    @GET("/en/API/Title/k_09ko5ajr/{id}/FullActor,Posters,Images,Trailer,Ratings,")
    suspend fun getShow(@Path("id")showId:String):ShowDetailDTO

//    @GET("/en/API/SearchMovie/k_95e4lc0l/{expression}")
//    fun searchMovie(@Path("expression") query: String): Call<SearchMovieResponse>
}

