package com.example.plantcarereminder.api

data class PlantApiResponse(
    val data: List<PlantData>
)

data class PlantData(
    val common_name: String?,
    val watering: String?,
    val default_image: ImageData?
)

data class ImageData(
    val regular_url: String?
)