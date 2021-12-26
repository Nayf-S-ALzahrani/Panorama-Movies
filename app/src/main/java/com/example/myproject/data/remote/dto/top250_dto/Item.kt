package com.example.myproject.data.remote.dto.top250_dto

import com.example.myproject.domain.model.top250movies.ItemTop250

data class Item(
    val crew: String,
    val fullTitle: String,
    val id: String,
    val imDbRating: String,
    val imDbRatingCount: String,
    val image: String,
    val rank: String,
    val title: String,
    val year: String
)

fun Item.toItemTop250(): ItemTop250 {
    return ItemTop250(
        crew = crew,
        top250ID = id,
        rating = imDbRating,
        image = image,
        title = title,
        year = year
    )
}