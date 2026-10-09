package com.example.ecommerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.Repository
import com.example.ecommerce.database.ProductEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    val products: StateFlow<List<ProductEntity>> =
        repository.products.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    val cartCount: StateFlow<Int> =
        repository.getCartCount()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                0
            )

    private val _searchQuery = MutableStateFlow("")

    val searchQuery: StateFlow<String> =
        _searchQuery.asStateFlow()

    private val _selectedCategory =
        MutableStateFlow("All")

    val selectedCategory: StateFlow<String> =
        _selectedCategory.asStateFlow()

    val categories: StateFlow<List<String>> =
        products
            .map { productList ->
                buildList {
                    add("All")

                    addAll(
                        productList
                            .map { it.category }
                            .distinct()
                            .sorted()
                    )
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                listOf("All")
            )

    val filteredProducts: StateFlow<List<ProductEntity>> =
        combine(
            products,
            _searchQuery,
            _selectedCategory
        ) { productList, query, category ->

            val normalizedQuery = query.trim()

            productList.filter { product ->

                val matchesSearch =
                    normalizedQuery.isEmpty() ||
                            product.name.contains(
                                normalizedQuery,
                                ignoreCase = true
                            )

                val matchesCategory =
                    category == "All" ||
                            product.category == category

                matchesSearch && matchesCategory
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    fun setSearchQuery(value: String) {
        _searchQuery.value = value
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun addToCart(product: ProductEntity) {
        viewModelScope.launch {
            repository.addToCart(product)
        }
    }
}