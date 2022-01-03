//package com.example.myproject.domain.use_case.get_info_by_id
//
//import com.example.myproject.common.Resource
//import com.example.myproject.data.remote.dto.title_dto.IDTitleDTO
//import com.example.myproject.domain.repository.MovieAndSeriesRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import retrofit2.Call
//import retrofit2.Response
//import javax.inject.Inject
//
//class GetInfoByIDUseCase @Inject constructor(
//    private val infoByIDRepository:MovieAndSeriesRepository
//) {
//    operator fun invoke():Flow<Resource<List<IDTitleDTO>>> = flow {
//        try{
//            emit(Resource.Loading())
//            val response = infoByIDRepository.getShow("id")
//            val infoById = response.body()!!.
//        }
//    }
//}