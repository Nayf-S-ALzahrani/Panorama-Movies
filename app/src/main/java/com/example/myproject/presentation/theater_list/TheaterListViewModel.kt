package com.example.myproject.presentation.theater_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.common.Resource
import com.example.myproject.domain.model.theater_movies.ItemTheater
import com.example.myproject.domain.use_case.get_theater.GetTheaterMoviesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

private const val TAG = "TheaterListViewModel"

@HiltViewModel
class TheaterListViewModel @Inject constructor(
    private val getTheaterMoviesUseCase: GetTheaterMoviesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<List<ItemTheater>>()
    val state: LiveData<List<ItemTheater>> = _state

    init {
        getTheater()
    }

    private fun getTheater() {
        getTheaterMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = result.data ?: emptyList()
//                    _state.value = Top250ListState(top250 = result.data ?: emptyList())
                    Log.d(TAG, "getTheater: ${result.data}")
                }
                is Resource.Error -> {
//                    _state.value = result.data(
//                        error = result.message ?: "An unexpected error occurred"
                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }
}



