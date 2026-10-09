package com.example.ecommerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.data.Repository

class AppViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(repository) as T

            modelClass.isAssignableFrom(CartViewModel::class.java) ->
                CartViewModel(repository) as T

            modelClass.isAssignableFrom(OrderViewModel::class.java) ->
                OrderViewModel(repository) as T

            else ->
                throw IllegalArgumentException(
                    "Unknown ViewModel: ${modelClass.name}"
                )
        }
    }
}
