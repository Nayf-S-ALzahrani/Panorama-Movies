package com.example.myproject.domain.repository

import com.example.myproject.data.remote.dto.coming_soon_dto.ComingSoonDTO
import com.example.myproject.data.remote.dto.popular_dto.MostPopularMoviesDTO
import com.example.myproject.data.remote.dto.recent_dto.RecentMoviesDTO
import com.example.myproject.data.remote.dto.title_dto.IDTitleDTO
import com.example.myproject.data.remote.dto.top250_dto.Top250MoviesDTO
import com.example.myproject.data.remote.dto.top250_tvs_dto.Top250TVsDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query

interface MovieAndSeriesRepository {

    suspend fun getRecentMovies():Response<RecentMoviesDTO>

    suspend fun getTop250Movies():Response<Top250MoviesDTO>

    suspend fun getMostPopularMovies():Response<MostPopularMoviesDTO>

    suspend fun getComingSoonMovies():Response<ComingSoonDTO>

    suspend fun getTop250TVs():Response<Top250TVsDTO>

    suspend fun getShow(query:String): Response<IDTitleDTO>

}