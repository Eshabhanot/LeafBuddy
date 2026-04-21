package com.example.plantcarereminder.api

import retrofit2.http.GET
import retrofit2.http.Query

interface PlantApi {

    @GET("species-list")
    suspend fun getPlants(
        @Query("key") apiKey: String
    ): PlantApiResponse
}