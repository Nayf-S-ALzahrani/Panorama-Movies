package com.example.myproject.presentation.home_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.common.Resource
import com.example.myproject.domain.use_case.get_coming_soon.GetComingSoonMoviesUseCase
import com.example.myproject.domain.use_case.get_recent.GetRecentMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "HomeListViewModel"

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val getComingSoonToHomeUseCase: GetComingSoonMoviesUseCase,
    private val getRecentToHomeUseCase: GetRecentMoviesUseCase,
) : ViewModel() {

    private val comingSoonToHome = MutableLiveData<HomeValue>()
    private val recentToHome = MutableLiveData<HomeValue>()

    val comingSoonHome: LiveData<HomeValue> = comingSoonToHome
    val recentHome: LiveData<HomeValue> = recentToHome

    init {
        getComingSoonToHome()
        getRecentToHome()
    }

    private fun getComingSoonToHome() {
        getComingSoonToHomeUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    comingSoonToHome.value = HomeValue(comingSoon = result.data?.filter { it ->
                        it.image.isNotBlank() && it.title.isNotBlank() && it.showTime.isNotBlank()
                    } ?: emptyList())
                    Log.d(TAG, "Get coming soon to home: Success ${result.data}")
                }
                is Resource.Error -> {
                    comingSoonToHome.value =
                        HomeValue(error = result.message ?: "An unexpected error occurred")
                    Log.d(TAG, "Get Coming Soon To Home: Error${result.message}")
                }
                is Resource.Loading -> {
                    comingSoonToHome.value = HomeValue(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRecentToHome() {
        getRecentToHomeUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    recentToHome.value = HomeValue(recent = result.data?.filter {
                        it.image.isNotBlank() && it.title.isNotBlank() && it.show_time.isNotBlank()
                    } ?: emptyList())
                    Log.d(TAG, "Get recent to home: ${result.data}")
                }
                is Resource.Error -> {
                    recentToHome.value =
                        HomeValue(error = result.message ?: "An unexpected error occurred")
                    Log.d(TAG, "ViewModel Get Recent To Home: ${result.message}")
                }
                is Resource.Loading -> {
                    recentToHome.value = HomeValue(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}