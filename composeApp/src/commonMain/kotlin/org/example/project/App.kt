package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.project.navigation.Screens
import org.example.project.screens.productdetail.ProductDetailScreen
import org.example.project.screens.productlist.ProductListScreen

@Composable
fun ProductBrowserApp() {
    remember { initProductKit() }

    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screens.ProductList
        ) {

            composable<Screens.ProductList> {
                ProductListScreen(
                    onNavigateToDetail = { productId ->
                        navController.navigate(
                            Screens.ProductDetail(productId)
                        )
                    }
                )
            }

            composable<Screens.ProductDetail> { backStackEntry ->

                val args = backStackEntry.toRoute<Screens.ProductDetail>()

                ProductDetailScreen(
                    productId = args.productId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}