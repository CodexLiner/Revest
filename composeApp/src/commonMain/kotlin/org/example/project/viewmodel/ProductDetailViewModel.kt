package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.cancel
import org.example.project.ProductKit
import org.example.project.models.Product

class ProductDetailViewModel : ViewModel() {
    private val getProductDetailUseCase = ProductKit.catalog.getProductDetailUseCase
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow<UiState<Product>>(UiState.Loading)
    val state: StateFlow<UiState<Product>> = _state.asStateFlow()

    private var lastLoadedId: Int? = null

    override fun onCleared() {
        scope.cancel()
    }

    fun load(productId: Int) {
        if (lastLoadedId == productId && _state.value is UiState.Success) return
        lastLoadedId = productId
        _state.value = UiState.Loading
        scope.launch {
            val result = runCatching {
                withContext(Dispatchers.IO) { getProductDetailUseCase(productId) }
            }
            _state.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}
