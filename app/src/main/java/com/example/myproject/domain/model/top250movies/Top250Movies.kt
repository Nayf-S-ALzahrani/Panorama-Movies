package com.example.myproject.domain.model.top250movies

data class Top250Movies(
    val errorMessage: String,
    val items: List<ItemTop250>
)