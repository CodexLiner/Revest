package org.example.project.navigation

object AppRoutes {
    const val ARG_PRODUCT_ID = "productId"
    const val ProductList = "product_list"
    const val ProductDetail = "product_detail/{$ARG_PRODUCT_ID}"

    fun productDetail(productId: Int): String = "product_detail/$productId"
}
