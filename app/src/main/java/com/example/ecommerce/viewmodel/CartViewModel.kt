package com.example.ecommerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.Repository
import com.example.ecommerce.database.ProductEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CheckoutUiState(
    val isCheckingOut: Boolean = false,
    val completedOrderId: Long? = null,
    val error: String? = null
)

class CartViewModel(
    private val repository: Repository
) : ViewModel() {

    val cart = repository.cart.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    val total = repository.getTotal().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        0.0
    )

    private val _checkoutState =
        MutableStateFlow(CheckoutUiState())

    val checkoutState =
        _checkoutState.asStateFlow()

    fun add(product: ProductEntity) {
        viewModelScope.launch {
            repository.addToCart(product)
        }
    }

    fun remove(productId: Int) {
        viewModelScope.launch {
            repository.removeFromCart(productId)
        }
    }

    fun checkout() {
        if (_checkoutState.value.isCheckingOut) return

        viewModelScope.launch {

            _checkoutState.value =
                CheckoutUiState(isCheckingOut = true)

            try {
                val orderId =
                    repository.placeOrder()

                _checkoutState.value =
                    CheckoutUiState(
                        completedOrderId = orderId
                    )

            } catch (e: Exception) {

                _checkoutState.value =
                    CheckoutUiState(
                        error =
                            e.message
                                ?: "Unable to place order."
                    )
            }
        }
    }

    fun consumeCheckoutResult() {
        _checkoutState.value =
            CheckoutUiState()
    }
}