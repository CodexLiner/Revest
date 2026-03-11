package org.example.project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val category: String? = null,
    val price: Double,
    val discountPercentage: Double? = null,
    val rating: Double,
    val stock: Int? = null,
    val tags: List<String> = emptyList(),
    val brand: String? = null,
    val sku: String? = null,
    val weight: Double? = null,
    val dimensions: DimensionsDto? = null,
    val warrantyInformation: String? = null,
    val shippingInformation: String? = null,
    val availabilityStatus: String? = null,
    val reviews: List<ReviewDto> = emptyList(),
    val returnPolicy: String? = null,
    val minimumOrderQuantity: Int? = null,
    val meta: MetaDto? = null,
    val thumbnail: String,
    val images: List<String> = emptyList()
)

@Serializable
data class DimensionsDto(
    val width: Double? = null,
    val height: Double? = null,
    val depth: Double? = null
)

@Serializable
data class ReviewDto(
    val rating: Double,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)

@Serializable
data class MetaDto(
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val barcode: String? = null,
    val qrCode: String? = null
)

@Serializable
data class ProductsResponseDto(
    @SerialName("products") val products: List<ProductDto>,
    @SerialName("total") val total: Int,
    @SerialName("skip") val skip: Int,
    @SerialName("limit") val limit: Int
)