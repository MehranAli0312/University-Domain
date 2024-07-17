package com.example.genesis.api

import com.example.genesis.models.UniversityModel
import com.example.genesis.models.UniversityModelItem
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


interface ApiServices {

    @GET("search")
    suspend fun getUniversities(
        @Query("country") searchQuery: String
    ): Response<UniversityModel>

}