package com.example.myproject.domain.repository

import com.example.myproject.data.remote.dto.popular.MostPopularMoviesDTO
import com.example.myproject.data.remote.dto.recent_dto.RecentMoviesDTO
import com.example.myproject.data.remote.dto.top250_dto.Top250MoviesDTO
import retrofit2.Response

interface MovieRepository {

    suspend fun getRecentMovies():Response<RecentMoviesDTO>

    suspend fun getTop250Movies():Response<Top250MoviesDTO>

    suspend fun getMostPopularMovies():Response<MostPopularMoviesDTO>

}