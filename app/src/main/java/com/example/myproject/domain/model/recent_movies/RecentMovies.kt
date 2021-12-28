package com.example.myproject.domain.model.recent_movies


data class RecentMovies(
    val errorMessage: String,
    val items: List<ItemRecent>
)