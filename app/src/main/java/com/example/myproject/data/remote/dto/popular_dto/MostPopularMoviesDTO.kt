package com.example.myproject.data.remote.dto.popular_dto

data class MostPopularMoviesDTO(
    val errorMessage: String,
    val items: List<Item>
)