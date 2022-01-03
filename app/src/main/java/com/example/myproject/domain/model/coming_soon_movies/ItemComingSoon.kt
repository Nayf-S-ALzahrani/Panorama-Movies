package com.example.myproject.domain.model.coming_soon_movies

import com.example.myproject.data.remote.dto.coming_soon_dto.Genre

data class ItemComingSoon(
    val age: String,
    val directors: String,
    val genreList: List<Genre>,
    val genres: String,
    val comingSoonID: String,
    val rating: String,
    val image: String,
    val plot: String,
    val showTime: String,
    val time: String,
    val stars: String,
    val title: String,
    val year: String
)
