package com.example.myproject.domain.use_case.get_top250_tvs

import android.util.Log
import com.example.myproject.common.Resource
import com.example.myproject.data.remote.dto.top250_tvs_dto.toItemTop250TVs
import com.example.myproject.domain.model.most_popular_movies.ItemPopular
import com.example.myproject.domain.model.top250_tvs.ItemTop250TVs
import com.example.myproject.domain.repository.MovieAndSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "GetTop250TVsUseCase"

class GetTop250TVsUseCase @Inject constructor(
    private val Top250TVsAndSeriesRepository: MovieAndSeriesRepository
) {
    operator fun invoke(): Flow<Resource<List<ItemTop250TVs>>> = flow {
        try {
            emit(Resource.Loading())
            val response = Top250TVsAndSeriesRepository.getTop250TVs()
            val top250TVs =
                response.body()!!.items.map { it.toItemTop250TVs() }
            if (top250TVs.isEmpty()) throw IllegalStateException(response.body()!!.errorMessage)
            emit(Resource.Success<List<ItemTop250TVs>>(top250TVs))
            Log.d(TAG, "$top250TVs")
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }catch (e: IllegalStateException) {
            emit(Resource.Error(" ${e.localizedMessage}" ?: "An unexpected error occurred"))
        }
    }
}
