package com.example.myproject.data.remote.dto.top250_tvs_dto

import com.example.myproject.domain.model.top250_tvs.ItemTop250TVs

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

fun Item.toItemTop250TVs(): ItemTop250TVs {
    return ItemTop250TVs(
        crew = crew,
        top250tvsID = id,
        rating = imDbRating,
        image = image,
        title = title,
        year = year
    )
}