package org.example.project.domain.repository

import org.example.project.models.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun searchProducts(query: String): List<Product>
    suspend fun getProductDetail(id: Int): Product
}
