package com.example.myproject.domain.model.detail

import com.example.myproject.data.remote.dto.detail_dto.*

data class ShowDetail(
    val actorList: List<Actor>?,
    val awards: String?,
    val age: String?,
    val directors: String?,
    val errorMessage: String?,
    val genres: String?,
    val id: String?,
    val rating: String?,
    val image: String?,
    val images: Images?,
    val languages: String?,
    val plot: String?,
    val posters: Posters?,
    val time: String?,
    val similars: List<Similar>?,
    val stars: String?,
    val tagline: String?,
    val title: String?,
    val trailer: Trailer?,
    val type: String?,
    val writers: String?,
    val year: String?
)