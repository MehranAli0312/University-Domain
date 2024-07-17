package com.example.genesis.repositories

import com.example.genesis.api.ApiServices
import com.example.genesis.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ApiRepository(
    private val apiServices: ApiServices,
) {
    suspend fun getUniversities(searchQuery: String) = flow {
        emit(DataStatus.loading())
        val result = apiServices.getUniversities(searchQuery)
        when (result.code()) {
            200 -> emit(DataStatus.success(result.body()))
            400 -> emit(DataStatus.error(result.message()))
            500 -> emit(DataStatus.error(result.message()))
        }
    }.catch {
        emit(DataStatus.error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}