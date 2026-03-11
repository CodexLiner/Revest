package org.example.project.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object ProductList : Screens

    @Serializable
    data class ProductDetail(val productId: Int) : Screens
}