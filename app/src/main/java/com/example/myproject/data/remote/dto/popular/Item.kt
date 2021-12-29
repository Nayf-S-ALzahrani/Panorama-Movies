package com.example.myproject.data.remote.dto.popular

import com.example.myproject.domain.model.most_popular_movies.ItemPopular

data class Item(
    val crew: String,
    val fullTitle: String,
    val id: String,
    val imDbRating: String,
    val imDbRatingCount: String,
    val image: String,
    val rank: String,
    val rankUpDown: String,
    val title: String,
    val year: String
)

fun Item.toItemPopular(): ItemPopular{
    return ItemPopular(
    crew = crew,
    popularId = id,
    rating = imDbRating,
    image = image,
    title = title,
    year = year
    )
}