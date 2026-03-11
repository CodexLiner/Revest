package org.example.project.data

import org.koin.dsl.module
import org.example.project.data.repository.ProductRepositoryImpl
import org.example.project.domain.repository.ProductRepository

val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImpl() }
}
