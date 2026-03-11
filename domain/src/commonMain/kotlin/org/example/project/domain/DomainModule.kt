package org.example.project.domain

import org.koin.dsl.module
import org.example.project.domain.usecase.GetProductDetailUseCase
import org.example.project.domain.usecase.GetProductsUseCase
import org.example.project.domain.usecase.SearchProductsUseCase

val domainModule = module {
    single { GetProductsUseCase(get()) }
    single { SearchProductsUseCase(get()) }
    single { GetProductDetailUseCase(get()) }
}
