package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.project.navigation.AppRoutes
import org.example.project.screens.productdetail.ProductDetailScreen
import org.example.project.screens.productlist.ProductListScreen

@Composable
fun ProductBrowserApp() {
    remember { initProductKit() }

    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppRoutes.ProductList
        ) {
            composable(AppRoutes.ProductList) {
                ProductListScreen(
                    onNavigateToDetail = { productId ->
                        navController.navigate(AppRoutes.productDetail(productId))
                    }
                )
            }
            composable(
                route = AppRoutes.ProductDetail,
                arguments = listOf(
                    navArgument(AppRoutes.ARG_PRODUCT_ID) { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt(AppRoutes.ARG_PRODUCT_ID) ?: 0
                ProductDetailScreen(
                    productId = productId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}