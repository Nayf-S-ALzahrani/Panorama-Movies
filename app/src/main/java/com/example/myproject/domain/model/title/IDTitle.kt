package com.example.myproject.domain.model.title

import com.example.myproject.data.remote.dto.title_dto.*

data class IDTitle(
    val actorList: List<Actor>,
    val awards: String,
    val companies: String,
    val age: String,
    val directors: String,
    val errorMessage: Any,
    val genres: String,
    val id: String,
    val rating: String,
    val image: String,
    val images: Images,
    val languages: String,
    val plot: String,
    val posters: Posters,
    val time: String,
    val similars: List<Similar>,
    val stars: String,
    val tagline: String,
    val title: String,
    val trailer: Trailer,
    val type: String,
    val writers: String,
    val year: String
)