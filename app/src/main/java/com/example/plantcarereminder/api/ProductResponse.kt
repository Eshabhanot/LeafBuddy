package com.example.plantcarereminder.api

data class ProductResponse(
    val products: List<Product>
)

data class Product(
    val title: String,
    val category: String,
    val thumbnail: String
)