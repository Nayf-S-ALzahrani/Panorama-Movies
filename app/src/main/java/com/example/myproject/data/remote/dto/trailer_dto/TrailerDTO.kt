package com.example.myproject.data.remote.dto.trailer_dto

import com.google.gson.annotations.SerializedName

data class TrailerDTO(
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