package com.example.myproject.data.remote.dto.coming_soon_dto

import com.example.myproject.domain.model.coming_soon_movies.ItemComingSoon

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

fun Item.toItemComingSoon(): ItemComingSoon {
    return ItemComingSoon(
        age = contentRating,
        directors = directors,
        genreList = genreList,
        genres = genres,
        comingSoonID = id,
        rating = imDbRating,
        image = image,
        plot = plot,
        showTime = releaseState,
        time = runtimeStr,
        stars = stars,
        title = title,
        year = year
    )
}
