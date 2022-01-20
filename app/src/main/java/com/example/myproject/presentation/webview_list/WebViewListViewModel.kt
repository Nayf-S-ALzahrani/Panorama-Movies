package com.example.myproject.presentation.webview_list

import android.util.Log
import androidx.lifecycle.*
import com.example.myproject.common.Constants
import com.example.myproject.common.Resource
import com.example.myproject.domain.use_case.get_detail.GeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "WebViewListViewModel"

@HiltViewModel
class WebViewListViewModel @Inject constructor(
    private val getDetailUseCase: GeDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableLiveData<WebViewValue>()
    val state: LiveData<WebViewValue> = _state

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
                        _state.value = WebViewValue(trailer = result.data)
                        Log.d(TAG, " WebView Value: ${result.data}")
                    }
                    is Resource.Error -> {
                        _state.value =
                            WebViewValue(error = result.message ?: "An unexpected error occurred")
                        Log.d(TAG, "Show detail viewModel: ${result.message}")
                    }
                    is Resource.Loading -> {
                        _state.value = WebViewValue(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }