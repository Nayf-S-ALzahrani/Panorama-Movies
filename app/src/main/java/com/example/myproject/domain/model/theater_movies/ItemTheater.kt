package com.example.myproject.domain.model.theater_movies

import com.example.myproject.data.remote.dto.theater_dto.Genre


data class ItemTheater(
    val contentRating: String,
    val directors: String,
    val genreList: List<Genre>,
    val genres: String,
    val theater_id: String,
    val rating: String,
    val image: String,
    val describe: String,
    val show_time: String,
    val time: String,
    val stars: String,
    val title: String,
    val year: String
)