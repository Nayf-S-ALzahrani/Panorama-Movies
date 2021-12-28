package com.example.myproject.data.remote.dto.recent_dto

import com.example.myproject.domain.model.recent_movies.ItemRecent

data class Item(
    val contentRating: String,
    val directorList: List<Director>,
    val directors: String,
    val fullTitle: String,
    val genreList: List<Genre>,
    val genres: String,
    val id: String,
    val imDbRating: String,
    val imDbRatingCount: String,
    val image: String,
    val metacriticRating: String,
    val plot: String,
    val releaseState: String,
    val runtimeMins: String,
    val runtimeStr: String,
    val starList: List<Star>,
    val stars: String,
    val title: String,
    val year: String
)

fun Item.toItemRecent(): ItemRecent {
    return ItemRecent(
    contentRating = contentRating,
    directors =  directors,
    genreList = genreList,
    genres = genres,
    theater_id = id,
    rating = imDbRating,
    image = image,
    describe = plot,
    show_time = releaseState,
    time = runtimeStr,
    stars = stars,
    title = title,
    year = year
    )
}