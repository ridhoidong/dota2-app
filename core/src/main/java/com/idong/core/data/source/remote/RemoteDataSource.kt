package com.idong.core.data.source.remote

import com.idong.core.data.source.remote.network.ApiResponse
import com.idong.core.data.source.remote.network.ApiService
import com.idong.core.data.source.remote.response.HeroResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ridhopratama on 30,August,2021
 */

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getListHero(): Flow<ApiResponse<List<HeroResponse>>> {
        return flow {
            try {
                val response = apiService.getListHero()
                val dataArray = response.data
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getListFeaturedHero(): Flow<ApiResponse<List<HeroResponse>>> {
        return flow {
            try {
                val response = apiService.getListFeaturedHero()
                val dataArray = response.data
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getListNewHero(): Flow<ApiResponse<List<HeroResponse>>> {
        return flow {
            try {
                val response = apiService.getListNewHero()
                val dataArray = response.data
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

}