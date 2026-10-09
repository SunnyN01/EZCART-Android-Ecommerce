package com.example.ecommerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.Repository
import com.example.ecommerce.database.OrderWithItems
import kotlinx.coroutines.flow.*

class OrderViewModel(
    private val repository: Repository
) : ViewModel() {

    val orders = repository.ordersWithItems.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun observeOrder(orderId: Long): Flow<OrderWithItems?> =
        repository.observeOrder(orderId)
}