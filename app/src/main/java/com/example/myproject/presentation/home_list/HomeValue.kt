package com.example.myproject.presentation.home_list

import com.example.myproject.domain.model.coming_soon_movies.ItemComingSoon
import com.example.myproject.domain.model.recent_movies.ItemRecent

class HomeValue(
    val isLoading: Boolean = false,
    val comingSoon: List<ItemComingSoon> = emptyList(),
    val recent:List<ItemRecent> = emptyList(),
    val error : String = ""
)