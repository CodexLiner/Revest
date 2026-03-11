package org.example.project.screens.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.components.NetworkImage
import org.example.project.models.Product
import org.example.project.models.Review
import org.example.project.screens.UiState
import kotlin.math.round

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBack: () -> Unit
) {

    val viewModel: ProductDetailViewModel = viewModel { ProductDetailViewModel() }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(productId) {
        viewModel.load(productId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            TopBar(onBack)

            when (val uiState = state) {

                is UiState.Success -> {

                    val product = uiState.data

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(bottom = 120.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        item {
                            ProductImage(product.images, product.thumbnail)
                        }

                        item {
                            ProductHeader(product)
                        }

                        item {
                            PriceSection(product.price, product.discountPercentage)
                        }

                        if (!product.description.isNullOrBlank()) {
                            item {
                                DescriptionSection(product.description)
                            }
                        }

                        item {
                            ProductInfoSection(product)
                        }

                        if (product.reviews.isNotEmpty()) {
                            item {
                                ReviewSection(product.reviews)
                            }
                        }
                    }

                    BottomBar()
                }

                is UiState.Error -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = uiState.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(onBack: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .height(56.dp)
    ) {

        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }

        Text(
            text = "Product Details",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ProductImage(images: List<String>, thumbnail: String?) {

    val image = images.firstOrNull() ?: thumbnail

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {

        if (!image.isNullOrBlank()) {
            NetworkImage(
                url = image,
                modifier = Modifier.fillMaxSize()
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
        ) {

            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null
            )
        }
    }
}

@Composable
fun ProductHeader(product: Product) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        if (!product.title.isNullOrBlank()) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        if (!product.brand.isNullOrBlank()) {

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.brand,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFF2E7D32)
            ) {

                Text(
                    text = "⭐ ${product.rating}",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            if (product.reviews.isNotEmpty()) {

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    "${product.reviews.size} reviews",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun PriceSection(price: Double?, discount: Double?) {

    if (price == null) return

    val discounted = if (discount != null) price - (price * discount / 100) else price
    val formatted = round(discounted * 100) / 100

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "$$formatted",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (discount != null && discount > 0) {

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "$$price",
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(6.dp)
            ) {

                Text(
                    text = "${discount.toInt()}% OFF",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun DescriptionSection(description: String) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                "Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(description)
        }
    }
}

@Composable
fun ProductInfoSection(product: Product) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                "Product Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (product.stock != null) InfoRow("Stock", product.stock.toString())
            if (!product.availabilityStatus.isNullOrBlank()) InfoRow("Availability", product.availabilityStatus)
            if (!product.sku.isNullOrBlank()) InfoRow("SKU", product.sku)
            if (product.weight != null) InfoRow("Weight", product.weight.toString())
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Text(
            value,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ReviewSection(reviews: List<Review>) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                "Reviews",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            reviews.forEach { review ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {

                    Text(
                        "⭐ ${review.rating}",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (!review.comment.isNullOrBlank()) {
                        Text(review.comment)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    if (!review.reviewerName.isNullOrBlank()) {
                        Text(
                            "- ${review.reviewerName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Divider()
            }
        }
    }
}

@Composable
fun BottomBar() {

    Surface(
        tonalElevation = 8.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {

                Text("Add to Cart")
            }

            Button(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {

                Text("Buy Now")
            }
        }
    }
}