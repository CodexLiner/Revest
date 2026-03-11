package org.example.project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val brand: String? = null,
    val thumbnail: String,
    val images: List<String> = emptyList()
)

@Serializable
data class ProductsResponseDto(
    @SerialName("products") val products: List<ProductDto>,
    @SerialName("total") val total: Int,
    @SerialName("skip") val skip: Int,
    @SerialName("limit") val limit: Int
)
