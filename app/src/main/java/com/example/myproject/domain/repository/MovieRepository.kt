package com.example.myproject.domain.repository

import com.example.myproject.data.remote.dto.theater_dto.TheaterMoviesDTO
import com.example.myproject.data.remote.dto.top250_dto.Top250MoviesDTO
import retrofit2.Response

interface MovieRepository {

    suspend fun getTheatersMovies():Response<TheaterMoviesDTO>

    suspend fun getTop250Movies():Response<Top250MoviesDTO>

}