package com.example.myproject.data.repository

import com.example.myproject.data.remote.ImdbApi
import com.example.myproject.data.remote.dto.popular.MostPopularMoviesDTO
import com.example.myproject.data.remote.dto.recent_dto.RecentMoviesDTO
import com.example.myproject.data.remote.dto.top250_dto.Top250MoviesDTO
import com.example.myproject.domain.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val api: ImdbApi
) : MovieRepository {

    override suspend fun getRecentMovies(): Response<RecentMoviesDTO> {
        return api.getRecentMovies()
    }

    override suspend fun getTop250Movies(): Response<Top250MoviesDTO> {
        return api.getTop250Movies()
    }

    override suspend fun getMostPopularMovies(): Response<MostPopularMoviesDTO> {
        return api.getMostPopular()
    }
}