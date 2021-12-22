package com.example.myproject.models.theater_movies

import com.google.gson.annotations.SerializedName

data class Trailer(
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val title: String,
    val type: String,
    val videoId: String,
    @SerializedName("videoUrl")
    val trailer: String,
    val year: String
)