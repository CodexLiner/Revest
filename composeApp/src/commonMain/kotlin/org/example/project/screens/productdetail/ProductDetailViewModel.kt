package org.example.project.screens.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.Revest
import org.example.project.models.Product
import org.example.project.screens.UiState

class ProductDetailViewModel : ViewModel() {

    private val getProductDetailUseCase = Revest.catalog.getProductDetailUseCase

    private val _state = MutableStateFlow<UiState<Product>>(UiState.Loading)
    val state: StateFlow<UiState<Product>> = _state.asStateFlow()

    private var lastLoadedId: Int? = null

    fun load(productId: Int) {
        if (lastLoadedId == productId && _state.value is UiState.Success) return

        lastLoadedId = productId
        _state.value = UiState.Loading

        viewModelScope.launch {
            val result = runCatching {
                withContext(Dispatchers.IO) {
                    getProductDetailUseCase(productId)
                }
            }

            _state.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}