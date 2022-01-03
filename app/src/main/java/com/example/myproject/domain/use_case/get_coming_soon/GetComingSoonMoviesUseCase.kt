package com.example.myproject.domain.use_case.get_coming_soon

import android.util.Log
import com.example.myproject.common.Resource
import com.example.myproject.data.remote.dto.coming_soon_dto.toItemComingSoon
import com.example.myproject.domain.model.coming_soon_movies.ItemComingSoon
import com.example.myproject.domain.repository.MovieAndSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "GetComingSoonMoviesUseCase"

class GetComingSoonMoviesUseCase @Inject constructor(
    private val comingSoonAndSeriesRepository: MovieAndSeriesRepository
) {
    operator fun invoke(): Flow<Resource<List<ItemComingSoon>>> = flow {
        try {
            emit(Resource.Loading())
            val response = comingSoonAndSeriesRepository.getComingSoonMovies()
            val comingSoon =
                response.body()!!.items.map { it.toItemComingSoon() }
//                filter { // sort the list
//                    it.image.isNotBlank()
//                }.map { it.toItemComingSoon() }.distinct()
            if (comingSoon.isEmpty()) throw IllegalStateException(response.body()!!.errorMessage)
            emit(Resource.Success<List<ItemComingSoon>>(comingSoon))
            Log.d(TAG, "$comingSoon")
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: IllegalStateException) {
            emit(Resource.Error(" ${e.localizedMessage}" ?: "An unexpected error occurred"))
        }
    }
}