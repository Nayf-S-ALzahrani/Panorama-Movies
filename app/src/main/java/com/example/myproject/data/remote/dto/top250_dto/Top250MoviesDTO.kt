package com.example.myproject.data.remote.dto.top250_dto

data class Top250MoviesDTO(
    val errorMessage: String,
    val items: List<Item>
)