package com.example.myproject.domain.use_case.get_popular

import android.util.Log
import com.example.myproject.common.Resource
import com.example.myproject.data.remote.dto.popular_dto.toItemPopular
import com.example.myproject.domain.model.most_popular_movies.ItemPopular
import com.example.myproject.domain.repository.MovieAndSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "GetPopularMoviesUseCase"

class GetPopularMoviesUseCase @Inject constructor(
    private val popularAndSeriesRepository: MovieAndSeriesRepository
) {
    operator fun invoke(): Flow<Resource<List<ItemPopular>>> = flow {
        try {
            emit(Resource.Loading())
            val response = popularAndSeriesRepository.getMostPopularMovies()
            val popular =
                response.body()!!.items.map { it.toItemPopular() }
            if (popular.isEmpty()) throw IllegalStateException(response.body()!!.errorMessage)
            emit(Resource.Success<List<ItemPopular>>(popular))
            Log.d(TAG, "$popular")
        } catch (e: HttpException) {//if response code not successful
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {//if our repository or API can not talk to or read to remote API EX: internet action
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }catch (e: IllegalStateException) {
            emit(Resource.Error(" ${e.localizedMessage}" ?: "An unexpected error occurred"))
        }
    }
}
