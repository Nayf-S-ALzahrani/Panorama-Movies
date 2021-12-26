package com.example.myproject.presentation.top250_list

import com.example.myproject.domain.model.top250movies.ItemTop250

data class Top250ListState(
    val isLoading: Boolean = false,
    val top250: List<ItemTop250> = emptyList(),
    val error : String = ""
)