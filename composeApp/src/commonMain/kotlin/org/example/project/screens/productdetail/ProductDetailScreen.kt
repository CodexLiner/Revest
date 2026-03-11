package org.example.project.screens.productdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.components.NetworkImage
import org.example.project.screens.UiState

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBack: () -> Unit
) {
    val viewModel : ProductDetailViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(productId) {
        viewModel.load(productId)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = onBack) {
            Text("Back")
        }

        when (val uiState = state) {
            is UiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            is UiState.Success -> {
                val product = uiState.data
                NetworkImage(
                    url = product.images.firstOrNull() ?: product.thumbnail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(text = product.description)
                Text(text = "Brand: ${product.brand}")
                Text(text = "Price: ${'$'}${product.price}")
                Text(text = "Rating: ${product.rating}")
            }
            is UiState.Navigation -> Unit
        }
    }
}
