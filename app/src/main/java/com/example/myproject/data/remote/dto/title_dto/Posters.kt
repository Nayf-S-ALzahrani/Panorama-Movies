package com.example.myproject.data.remote.dto.title_dto

data class Posters(
    val backdrops: List<Backdrop>,
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val posters: List<Poster>,
    val title: String,
    val type: String,
    val year: String
)