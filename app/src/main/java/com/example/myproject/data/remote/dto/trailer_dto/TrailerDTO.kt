package com.example.myproject.data.remote.dto.trailer_dto


data class TrailerDTO(
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val title: String,
    val type: String,
    val videoId: String,
    val videoUrl: String,
    val year: String
)