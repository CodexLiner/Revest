package org.example.project.data.remote

import org.example.project.data.dto.ProductDto
import org.example.project.data.dto.ProductsResponseDto
import org.example.project.network.NetworkClient

interface ProductRemoteDataSource {
    suspend fun getProducts(): ProductsResponseDto
    suspend fun searchProducts(query: String): ProductsResponseDto
    suspend fun getProductDetail(id: Int): ProductDto
}

class ProductRemoteDataSourceImpl : ProductRemoteDataSource {
    private val api = ProductApi(NetworkClient.client)

    override suspend fun getProducts(): ProductsResponseDto = api.getProducts()

    override suspend fun searchProducts(query: String): ProductsResponseDto = api.searchProducts(query)

    override suspend fun getProductDetail(id: Int): ProductDto = api.getProductDetail(id)
}
