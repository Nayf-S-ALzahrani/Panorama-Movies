package com.example.myproject.data.remote.dto.detail_dto

import android.util.Log
import com.example.myproject.domain.model.detail.ShowDetail

private const val TAG = "ShowDetailDTO"

data class ShowDetailDTO(
    val actorList: List<Actor>?,
    val awards: String?,
    val boxOffice: BoxOffice?,
    val companies: String?,
    val companyList: List<Company>?,
    val contentRating: String?,
    val countries: String?,
    val countryList: List<Country?>,
    val directorList: List<Director>?,
    val directors: String?,
    val errorMessage: String?,
    val fullCast: Any?,
    val fullTitle: String?,
    val genreList: List<Genre>?,
    val genres: String?,
    val id: String?,
    val imDbRating: String?,
    val imDbRatingVotes: String?,
    val image: String?,
    val images: Images?,
    val keywordList: List<String>?,
    val keywords: String?,
    val languageList: List<Language>?,
    val languages: String?,
    val metacriticRating: String?,
    val originalTitle: String?,
    val plot: String?,
    val plotLocal: String?,
    val plotLocalIsRtl: Boolean?,
    val posters: Posters?,
    val ratings: Ratings?,
    val releaseDate: String?,
    val runtimeMins: String?,
    val runtimeStr: String?,
    val similars: List<Similar>?,
    val starList: List<Star>?,
    val stars: String?,
    val tagline: String?,
    val title: String?,
    val trailer: Trailer?,
    val tvEpisodeInfo: Any?,
    val tvSeriesInfo: Any?,
    val type: String?,
    val wikipedia: Any?,
    val writerList: List<Writer>?,
    val writers: String?,
    val year: String?
)

fun ShowDetailDTO.toShowDetail(): ShowDetail {
    return ShowDetail(
        starList = starList,
        actorList = actorList,
        awards = awards,
        age = contentRating,
        directors = directors,
        errorMessage = errorMessage,
        genres = genres,
        id = id,
        rating = imDbRating,
        image = image,
        images = images,
        languages = languages,
        plot = plot,
        posters = posters,
        time = runtimeStr,
        similars = similars,
        stars = stars,
        tagline = tagline,
        title = title,
        trailer = trailer,
        type = type,
        writers = writers,
        year = year
    )
}