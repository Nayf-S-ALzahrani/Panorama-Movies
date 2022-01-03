package com.example.myproject.data.remote

import com.example.myproject.data.remote.dto.coming_soon_dto.ComingSoonDTO
import com.example.myproject.data.remote.dto.popular_dto.MostPopularMoviesDTO
import com.example.myproject.data.remote.dto.recent_dto.RecentMoviesDTO
import com.example.myproject.data.remote.dto.title_dto.IDTitleDTO
import com.example.myproject.data.remote.dto.top250_dto.Top250MoviesDTO
import com.example.myproject.data.remote.dto.top250_tvs_dto.Top250TVsDTO
import com.example.myproject.domain.model.trailer.Trailer
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY ="k_09ko5ajr"//"k_l826iwT6" - "k_5lbkeqi2"
//https://imdb-api.com/Identity/Account/Manage

interface ImdbApi {
    @GET("/en/API/InTheaters/k_09ko5ajr")
    suspend fun getRecentMovies(): Response<RecentMoviesDTO>

    @GET("/en/API/YouTubeTrailer/k_09ko5ajr")
    suspend fun getTrailer(@Path("Id") id: String):Response<Trailer>

    @GET("/en/API/Top250Movies/k_09ko5ajr")
    suspend fun getTop250Movies():Response<Top250MoviesDTO>

    @GET("/en/API/MostPopularMovies/k_09ko5ajr")
    suspend fun getMostPopular():Response<MostPopularMoviesDTO>

    @GET("/en/API/ComingSoon/k_09ko5ajr")
    suspend fun getComingSoon():Response<ComingSoonDTO>

    @GET("/en/API/Top250TVs/k_09ko5ajr")
    suspend fun getTop250TVs():Response<Top250TVsDTO>

    @GET("/en/API/Title/k_l826iwT6/tt1375666/FullActor,Posters,Images,Trailer,Ratings,")
    suspend fun getShow(@Query("id")query:String):Response<IDTitleDTO>

//    @GET("/en/API/SearchMovie/k_95e4lc0l/{expression}")
//    fun searchMovie(@Path("expression") query: String): Call<SearchMovieResponse>
}

