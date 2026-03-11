package org.example.project.catalog

import org.example.project.domain.usecase.GetProductDetailUseCase
import org.example.project.domain.usecase.GetProductsUseCase
import org.example.project.domain.usecase.SearchProductsUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Catalog : KoinComponent {
    val getProductsUseCase by inject<GetProductsUseCase>()
    val searchProductsUseCase by inject<SearchProductsUseCase>()
    val getProductDetailUseCase by inject<GetProductDetailUseCase>()
}
