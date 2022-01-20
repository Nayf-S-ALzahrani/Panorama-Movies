package com.example.myproject.presentation.recent_list

import android.util.Log
import androidx.lifecycle.*
import com.example.myproject.common.Constants
import com.example.myproject.common.Resource
import com.example.myproject.domain.use_case.get_recent.GetRecentMoviesUseCase
import com.example.myproject.presentation.ui.authentication.Person
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

private const val TAG = "RecentListViewModel"

@HiltViewModel
class RecentListViewModel @Inject constructor(
    private val getRecentMoviesUseCase: GetRecentMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableLiveData<RecentValue>()
    val state: LiveData<RecentValue> = _state

    var position = 0

    val person = Person()

    init {
        getRecent()
        savedStateHandle.get<Int>(Constants.POSITION)?.let { sentPosition ->
            position = sentPosition
        }
    }

    private fun getRecent() {
        getRecentMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RecentValue(recent = result.data?.filter {
                        it.image.isNotBlank() && it.title.isNotBlank() && it.show_time.isNotBlank()
                    } ?: emptyList())

                    Log.d(TAG, "getRecent: ${result.data}")
                }
                is Resource.Error -> {
                    _state.value =
                        RecentValue(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = RecentValue(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}



