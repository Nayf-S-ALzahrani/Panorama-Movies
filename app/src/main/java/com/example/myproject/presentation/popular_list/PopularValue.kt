package com.example.myproject.presentation.popular_list

import com.example.myproject.domain.model.most_popular_movies.ItemPopular

class PopularValue (
    val isLoading: Boolean = false,
    val popular: List<ItemPopular> = emptyList(),
    val error : String = ""
)