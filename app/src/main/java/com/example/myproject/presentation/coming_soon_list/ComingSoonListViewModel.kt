package com.example.myproject.presentation.coming_soon_list

import android.util.Log
import androidx.lifecycle.*
import com.example.myproject.common.Constants
import com.example.myproject.common.Resource
import com.example.myproject.domain.use_case.get_coming_soon.GetComingSoonMoviesUseCase
import com.example.myproject.presentation.top250_list.Top250Value
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.FieldPosition
import javax.inject.Inject
import javax.persistence.criteria.CriteriaBuilder

private const val TAG = "ComingSoonListViewModel"

@HiltViewModel
class ComingSoonListViewModel @Inject constructor(
    private val getComingSoonMoviesUseCase: GetComingSoonMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableLiveData<ComingSoonValue>()
    val state: LiveData<ComingSoonValue> = _state

    var position = 0

    init {
        getComingSoon()

       savedStateHandle.get<Int>(Constants.POSITION)?.let { sentPosition ->
           position = sentPosition
       }
    }

    private fun getComingSoon() {
        getComingSoonMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ComingSoonValue(comingSoon = result.data?.filter {
                        it.image.isNotBlank() && it.title.isNotBlank() && it.showTime.isNotBlank()
                    } ?: emptyList())
                    Log.d(TAG, "getComingSoon: ${result.data}")
                }
                is Resource.Error -> {
                    _state.value =
                        ComingSoonValue(error = result.message ?: "An unexpected error occurred")
                    Log.d(TAG, "ViewModel getComingSoon: ${result.message}")
                }
                is Resource.Loading -> {
                    _state.value = ComingSoonValue(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}