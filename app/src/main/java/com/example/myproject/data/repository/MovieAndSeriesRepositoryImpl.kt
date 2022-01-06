package com.example.myproject.data.repository

import com.example.myproject.data.remote.ImdbApi
import com.example.myproject.data.remote.dto.coming_soon_dto.ComingSoonDTO
import com.example.myproject.data.remote.dto.popular_dto.MostPopularMoviesDTO
import com.example.myproject.data.remote.dto.recent_dto.RecentMoviesDTO
import com.example.myproject.data.remote.dto.detail_dto.ShowDetailDTO
import com.example.myproject.data.remote.dto.top250_dto.Top250MoviesDTO
import com.example.myproject.data.remote.dto.top250_tvs_dto.Top250TVsDTO
import com.example.myproject.domain.repository.MovieAndSeriesRepository
import retrofit2.Response
import javax.inject.Inject


class MovieAndSeriesRepositoryImpl @Inject constructor(
    private val api: ImdbApi
) : MovieAndSeriesRepository {

    override suspend fun getRecentMovies(): Response<RecentMoviesDTO> {
        return api.getRecentMovies()
    }

    override suspend fun getTop250Movies(): Response<Top250MoviesDTO> {
        return api.getTop250Movies()
    }

    override suspend fun getMostPopularMovies(): Response<MostPopularMoviesDTO> {
        return api.getMostPopular()
    }

    override suspend fun getComingSoonMovies(): Response<ComingSoonDTO> {
        return api.getComingSoon()
    }

    override suspend fun getTop250TVs(): Response<Top250TVsDTO> {
        return api.getTop250TVs()
    }

    override suspend fun getShowById(showId:String): ShowDetailDTO {
        return api.getShow(showId)
    }
}