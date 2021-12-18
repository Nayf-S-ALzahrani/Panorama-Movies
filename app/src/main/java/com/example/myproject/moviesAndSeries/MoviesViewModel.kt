package com.example.myproject.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.models.Item
import com.example.myproject.repo.ImdbRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val theatersRepo: ImdbRepo = ImdbRepo()
    private val theaterMovie: MutableLiveData<List<Item>> = MutableLiveData()
    val result: LiveData<List<Item>> = theaterMovie


    fun getTheaters() {
        viewModelScope.launch {
            theatersRepo.getData.collect {
                theaterMovie.value = it
            }
        }
    }
//    val dataLiveData: LiveData<List<Item>> = theatersRepo.getTheatersMovies()

}