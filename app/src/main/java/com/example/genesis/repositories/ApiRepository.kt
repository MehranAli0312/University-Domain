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
    // Function to get universities based on a search query
    suspend fun getUniversities(searchQuery: String) = flow {
        // Emit loading status
        emit(DataStatus.loading())

        // Make the API call to get universities
        val result = apiServices.getUniversities(searchQuery)

        // Handle the response based on the status code
        when (result.code()) {
            200 -> emit(DataStatus.success(result.body())) // Success
            400 -> emit(DataStatus.error("Bad Request: The server could not understand the request. Please check your input and try again."))
            401 -> emit(DataStatus.error("Unauthorized: You are not authorized to access this resource. Please check your credentials."))
            403 -> emit(DataStatus.error("Forbidden: You do not have permission to access this resource."))
            404 -> emit(DataStatus.error("Not Found: The requested resource could not be found. Please try again."))
            500 -> emit(DataStatus.error("Server Error: There is an issue with the server. Please try again later."))
            502 -> emit(DataStatus.error("Bad Gateway: The server received an invalid response. Please try again later."))
            503 -> emit(DataStatus.error("Service Unavailable: The server is currently unavailable. Please try again later."))
            else -> emit(DataStatus.error("Unexpected Error: Something went wrong. Please try again."))
        }
    }.catch { exception ->
        // Handle any exceptions and emit an error status
        emit(DataStatus.error(exception.message.toString()))
    }.flowOn(Dispatchers.IO) // Execute this flow on the IO dispatcher for better performance
}
