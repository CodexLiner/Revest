package org.example.project.data.repository

import org.example.project.data.mapper.toDomain
import org.example.project.data.remote.ProductRemoteDataSourceImpl
import org.example.project.models.Product
import org.example.project.domain.repository.ProductRepository

class ProductRepositoryImpl(
) : ProductRepository {
    private val remoteDataSource = ProductRemoteDataSourceImpl()

    override suspend fun getProducts(): List<Product> {
        return remoteDataSource.getProducts().products.map { it.toDomain() }
    }

    override suspend fun searchProducts(query: String): List<Product> {
        return remoteDataSource.searchProducts(query).products.map { it.toDomain() }
    }

    override suspend fun getProductDetail(id: Int): Product {
        return remoteDataSource.getProductDetail(id).toDomain()
    }
}
