package com.example.myproject.data.remote.dto.detail_dto

data class Images(
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val items: List<Item>,
    val title: String,
    val type: String,
    val year: String
)