package com.example.myproject.data.remote.dto.popular

data class MostPopularMoviesDTO(
    val errorMessage: String,
    val items: List<Item>
)