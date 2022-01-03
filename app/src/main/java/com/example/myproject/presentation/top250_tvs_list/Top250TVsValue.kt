package com.example.myproject.presentation.top250_tvs_list

import com.example.myproject.domain.model.top250_tvs.ItemTop250TVs

class Top250TVsValue (
    val isLoading: Boolean = false,
    val top250TVs: List<ItemTop250TVs> = emptyList(),
    val error : String = ""
)