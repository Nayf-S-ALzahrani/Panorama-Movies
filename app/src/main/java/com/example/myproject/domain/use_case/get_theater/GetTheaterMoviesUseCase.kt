package com.example.myproject.domain.use_case.get_theater

import android.util.Log
import com.example.myproject.common.Resource
import com.example.myproject.data.remote.dto.theater_dto.toItemTheater
import com.example.myproject.domain.model.theater_movies.ItemTheater
import com.example.myproject.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "GetTheaterMoviesUseCase"

class GetTheaterMoviesUseCase @Inject constructor(
    private val theaterRepository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<ItemTheater>>> = flow {
        try {
            emit(Resource.Loading())
            val theaters = theaterRepository.getTheatersMovies().body()!!.items.map { it.toItemTheater() }
            emit(Resource.Success<List<ItemTheater>>(theaters))
            Log.d(TAG, "$theaters")
            Log.d(TAG, "theaters")
        } catch (e: HttpException) {//if response code not successful
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {//if our repository or API can not talk to or read to remote API EX: internet action
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}