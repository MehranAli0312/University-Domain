package com.example.genesis.api

import com.example.genesis.models.UniversityModel
import com.example.genesis.models.UniversityModelItem
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


// Interface defining the API services for fetching university data
interface ApiServices {

    // Function to search for universities based on a country name
    @GET("search")
    suspend fun getUniversities(
        @Query("country") searchQuery: String // Query parameter to filter universities by country
    ): Response<UniversityModel> // The API response containing a UniversityModel object
}
