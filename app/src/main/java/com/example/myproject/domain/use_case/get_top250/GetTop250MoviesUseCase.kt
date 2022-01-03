package com.example.myproject.domain.use_case.get_top250

import com.example.myproject.common.Resource
import com.example.myproject.data.remote.dto.top250_dto.toItemTop250
import com.example.myproject.domain.model.top250_movies.ItemTop250
import com.example.myproject.domain.repository.MovieAndSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTop250MoviesUseCase @Inject constructor(
    private val topMoviesAndSeriesRepository: MovieAndSeriesRepository
) {
    operator fun invoke(): Flow<Resource<List<ItemTop250>>> = flow {
        try {
            emit(Resource.Loading())
            val response = topMoviesAndSeriesRepository.getTop250Movies()
            val topMovies = response.body()!!.items.map { it.toItemTop250() }
            if (topMovies.isEmpty()) throw IllegalStateException(response.body()!!.errorMessage)
            emit(Resource.Success(topMovies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }catch (e: IllegalStateException) {
            emit(Resource.Error(" ${e.localizedMessage}" ?: "An unexpected error occurred"))
        }
    }
}