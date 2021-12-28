package com.example.myproject.domain.use_case.get_recent

import android.util.Log
import com.example.myproject.common.Resource
import com.example.myproject.data.remote.dto.recent_dto.toItemRecent
import com.example.myproject.domain.model.recent_movies.ItemRecent
import com.example.myproject.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "GetRecentMoviesUseCase"

class GetRecentMoviesUseCase @Inject constructor(
    private val RecentRepository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<ItemRecent>>> = flow {
        try {
            emit(Resource.Loading())
            val recent = RecentRepository.getRecentMovies().body()!!.items.map { it.toItemRecent() }
            emit(Resource.Success<List<ItemRecent>>(recent))
            Log.d(TAG, "$recent")
            Log.d(TAG, "recent")
        } catch (e: HttpException) {//if response code not successful
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {//if our repository or API can not talk to or read to remote API EX: internet action
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}