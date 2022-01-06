package com.example.myproject.domain.use_case.get_detail

import com.example.myproject.common.Resource
import com.example.myproject.data.remote.dto.detail_dto.toShowDetail
import com.example.myproject.domain.model.detail.ShowDetail
import com.example.myproject.domain.repository.MovieAndSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GeDetailUseCase @Inject constructor(
    private val detailRepository: MovieAndSeriesRepository
) {
    operator fun invoke(showId: String): Flow<Resource<ShowDetail>> = flow {
        try {
            emit(Resource.Loading())
            val show = detailRepository.getShowById(showId).toShowDetail()
//            val show = response.body()!!.toShow
            emit(Resource.Success(show))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: IllegalStateException) {
            emit(Resource.Error(" ${e.localizedMessage}" ?: "An unexpected error occurred"))
        }
    }
}