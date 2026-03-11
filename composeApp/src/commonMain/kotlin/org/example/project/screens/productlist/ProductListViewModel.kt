package org.example.project.screens.productlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.Revest
import org.example.project.models.Product
import org.example.project.screens.NavigationDestination
import org.example.project.screens.UiState

class ProductListViewModel : ViewModel() {
    private val getProductsUseCase = Revest.catalog.getProductsUseCase
    private val searchProductsUseCase = Revest.catalog.searchProductsUseCase
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Product>>> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var cachedProducts: List<Product> = emptyList()

    fun loadInitial() {
        if (cachedProducts.isEmpty()) {
            loadProducts()
        } else {
            _state.value = UiState.Success(cachedProducts)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _query.value = query
        if (query.isBlank()) {
            loadProducts()
        } else {
            searchProducts(query)
        }
    }

    fun onProductSelected(productId: Int) {
        _state.value = UiState.Navigation(NavigationDestination.ProductDetail(productId))
    }

    fun onNavigationHandled() {
        _state.value = UiState.Success(cachedProducts)
    }

    override fun onCleared() {
        scope.cancel()
    }

    private fun loadProducts() {
        _state.value = UiState.Loading
        scope.launch {
            val result = runCatching {
                withContext(Dispatchers.IO) { getProductsUseCase() }
            }
            _state.value = result.fold(
                onSuccess = {
                    cachedProducts = it
                    UiState.Success(it)
                },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    private fun searchProducts(query: String) {
        _state.value = UiState.Loading
        scope.launch {
            val result = runCatching {
                withContext(Dispatchers.IO) { searchProductsUseCase(query) }
            }
            _state.value = result.fold(
                onSuccess = {
                    cachedProducts = it
                    UiState.Success(it)
                },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}