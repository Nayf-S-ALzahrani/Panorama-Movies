package com.example.myproject.presentation.recent_list

import com.example.myproject.domain.model.recent_movies.ItemRecent

data class RecentValue(
    val isLoading: Boolean = false,
    val recent: List<ItemRecent> = emptyList(),
    val error : String = ""
)