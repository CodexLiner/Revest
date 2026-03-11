package org.example.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.project.data.dto.ProductDto
import org.example.project.data.dto.ProductsResponseDto

class ProductApi(
    private val client: HttpClient
) {
    private val baseUrl = "https://dummyjson.com"

    suspend fun getProducts(): ProductsResponseDto {
        return client.get("$baseUrl/products").body()
    }

    suspend fun searchProducts(query: String): ProductsResponseDto {
        return client.get("$baseUrl/products/search") {
            parameter("q", query)
        }.body()
    }

    suspend fun getProductDetail(id: Int): ProductDto {
        return client.get("$baseUrl/products/$id").body()
    }
}
