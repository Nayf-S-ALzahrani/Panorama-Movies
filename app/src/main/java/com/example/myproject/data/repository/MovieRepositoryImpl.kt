package com.example.myproject.data.repository

import com.example.myproject.data.remote.ImdbApi
import com.example.myproject.data.remote.dto.theater_dto.TheaterMoviesDTO
import com.example.myproject.data.remote.dto.top250_dto.Top250MoviesDTO
import com.example.myproject.domain.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val api: ImdbApi
) : MovieRepository {

    override suspend fun getTheatersMovies(): Response<TheaterMoviesDTO> {
        return api.getTheatersMovies()
    }

    override suspend fun getTop250Movies(): Response<Top250MoviesDTO> {
        return api.getTop250Movies()
    }
}