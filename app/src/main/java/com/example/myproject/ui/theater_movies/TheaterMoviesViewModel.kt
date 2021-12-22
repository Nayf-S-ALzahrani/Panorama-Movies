package com.example.myproject.ui.theater_movies

import androidx.lifecycle.ViewModel
import com.example.myproject.models.theater_movies.Item
import com.example.myproject.repo.ImdbRepo
import kotlinx.coroutines.flow.Flow


class TheaterMoviesViewModel : ViewModel() {
    private val theatersRepo: ImdbRepo = ImdbRepo()

    fun getDataTheaters(): Flow<List<Item>> {
        return theatersRepo.getDataTheatersRepo
    }
}