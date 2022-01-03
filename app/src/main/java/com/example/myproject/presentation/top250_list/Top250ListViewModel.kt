package com.example.myproject.presentation.top250_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.common.Resource
import com.example.myproject.domain.use_case.get_top250.GetTop250MoviesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel as HiltViewModel1

private const val TAG = "Top250ListViewModel"

@HiltViewModel1
class Top250ListViewModel @Inject constructor(
    private val getTop250MoviesUseCase: GetTop250MoviesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<Top250Value>()
    val state: LiveData<Top250Value> = _state

    init {
        getTop250()
    }

    private fun getTop250() {
        getTop250MoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = Top250Value(top250 = result.data?.filter {
                        it.rating.isNotBlank() && it.image.isNotBlank() && it.title.isNotBlank()
                    } ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        Top250Value(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = Top250Value(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}



