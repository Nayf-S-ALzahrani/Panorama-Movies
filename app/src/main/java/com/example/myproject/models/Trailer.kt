package com.example.myproject.models

import com.google.gson.annotations.SerializedName

data class Trailer(
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val title: String,
    val type: String,
    val videoId: String,
    @SerializedName("trailer")
    val videoUrl: String,
    val year: String
)