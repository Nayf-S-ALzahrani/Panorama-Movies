package com.example.myproject.presentation.coming_soon_list

import com.example.myproject.domain.model.coming_soon_movies.ItemComingSoon


class ComingSoonValue (
    val isLoading: Boolean = false,
    val comingSoon: List<ItemComingSoon> = emptyList(),
    val error : String = ""
)