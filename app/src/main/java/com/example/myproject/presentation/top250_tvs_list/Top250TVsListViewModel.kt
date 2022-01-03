package com.example.myproject.presentation.top250_tvs_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.common.Resource
import com.example.myproject.domain.use_case.get_top250_tvs.GetTop250TVsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "Top250TVsListViewModel"

@HiltViewModel
class Top250TVsListViewModel @Inject constructor(
    private val getTop250TVsUseCase: GetTop250TVsUseCase
) : ViewModel() {
    private val _state = MutableLiveData<Top250TVsValue>()
    val state: LiveData<Top250TVsValue> = _state

    init {
        getTop250TVs()
    }

    private fun getTop250TVs() {
        getTop250TVsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = Top250TVsValue(top250TVs = result.data?.filter {
                        it.rating.isNotBlank() && it.image.isNotBlank() && it.title.isNotBlank()
                    } ?: emptyList())
                    Log.d(TAG, "getTop250TVs: ${result.data}")
                }
                is Resource.Error -> {
                    _state.value =
                        Top250TVsValue(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = Top250TVsValue(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
