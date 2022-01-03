package com.example.myproject.presentation.popular_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.common.Resource
import com.example.myproject.domain.use_case.get_popular.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "PopularListViewModel"

@HiltViewModel
class PopularListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    private val _state = MutableLiveData<PopularValue>()
    val state: LiveData<PopularValue> = _state

    init {
        getPopular()
    }

    private fun getPopular() {
        getPopularMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PopularValue(popular = result.data?.filter {
                        it.rating.isNotBlank() && it.image.isNotBlank() && it.title.isNotBlank()
                    } ?: emptyList())
                    Log.d(TAG, "getRecent: ${result.data}")
                }
                is Resource.Error -> {
                    _state.value =
                        PopularValue(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = PopularValue(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
