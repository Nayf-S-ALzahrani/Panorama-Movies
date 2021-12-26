package com.example.myproject.domain.use_case.get_top250

import com.example.myproject.common.Resource
import com.example.myproject.data.remote.dto.top250_dto.toItemTop250
import com.example.myproject.domain.model.top250movies.ItemTop250
import com.example.myproject.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTop250MoviesUseCase @Inject constructor(
    private val topMoviesRepository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<ItemTop250>>> = flow {
        try {
            emit(Resource.Loading())
            val topMovies =
                topMoviesRepository.getTop250Movies().body()!!.items.map { it.toItemTop250() }
            emit(Resource.Success(topMovies))
        } catch (e: HttpException) {//if response code not successful
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {//if our repository or API can not talk to or read to remote API EX: internet action
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}