package com.example.myproject.domain.model.coming_soon_movies

data class ComingSoonMovies(
    val errorMessage: String,
    val items: List<ItemComingSoon>
)