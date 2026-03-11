package org.example.project.models

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val brand: String,
    val price: Double,
    val rating: Double,
    val thumbnail: String,
    val images: List<String>
)
