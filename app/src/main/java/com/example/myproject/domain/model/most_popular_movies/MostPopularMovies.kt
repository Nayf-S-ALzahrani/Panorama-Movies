package com.example.myproject.domain.model.most_popular_movies

data class MostPopularMovies(
    val errorMessage: String,
    val items: List<ItemPopular>
)