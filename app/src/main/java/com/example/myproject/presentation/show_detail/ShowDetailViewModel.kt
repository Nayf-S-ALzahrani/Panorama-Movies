package com.example.myproject.presentation.show_detail

import android.util.Log
import androidx.lifecycle.*
import com.example.myproject.common.Constants
import com.example.myproject.common.Resource
import com.example.myproject.domain.use_case.get_detail.GeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "ShowDetailViewModel"

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val getDetailUseCase: GeDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableLiveData<DetailValue>()
    val state: LiveData<DetailValue> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_SHOW_ID)?.let { showId ->
            Log.d(TAG, "SAVED STATE ID: $showId")
            getDetail(showId)
        }
    }

    private fun getDetail(showId: String) {
        getDetailUseCase(showId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DetailValue(show = result.data)
                    Log.d(TAG, " Detail101: ${result.data}")
                }
                is Resource.Error -> {
                    _state.value =
                        DetailValue(error = result.message ?: "An unexpected error occurred")
                    Log.d(TAG, "Show detail viewModel: ${result.message}")
                }
                is Resource.Loading -> {
                    _state.value = DetailValue(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

