package com.example.myproject.ui.top_250_movies

import androidx.lifecycle.ViewModel
import com.example.myproject.models.top250movies.Item
import com.example.myproject.repo.ImdbRepo
import kotlinx.coroutines.flow.Flow

class Top250MoviesViewModel : ViewModel() {
    private val top250MoviesRepo: ImdbRepo = ImdbRepo()

    fun getTop250MoviesRepo(): Flow<List<Item>> {
        return top250MoviesRepo.getDataTop250MoviesRepo
    }
}