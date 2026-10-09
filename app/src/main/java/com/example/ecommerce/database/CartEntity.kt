package com.example.ecommerce.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String = ""
)