package com.example.myproject.presentation.recent_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.common.Resource
import com.example.myproject.domain.use_case.get_recent.GetRecentMoviesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

private const val TAG = "RecentListViewModel"

@HiltViewModel
class RecentListViewModel @Inject constructor(
    private val getRecentMoviesUseCase: GetRecentMoviesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<RecentValue>()
    val state: LiveData<RecentValue> = _state

    init {
        getRecent()
    }

    private fun getRecent() {
        getRecentMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RecentValue(recent = result.data ?: emptyList())
//                    _state.value = result.data ?: emptyList()
//                    _state.value = Top250ListState(top250 = result.data ?: emptyList())
                    Log.d(TAG, "getRecent: ${result.data}")
                }
                is Resource.Error -> {
                    _state.value = RecentValue(error = result.message ?: "An unexpected error occurred")
//                    _state.value = result.message.data(
//                        error = result.message ?: "An unexpected error occurred"
                }
                is Resource.Loading -> {
                    _state.value = RecentValue(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}



