package com.example.ecommerce.database

import androidx.compose.runtime.Immutable
import androidx.room.Entity

@Immutable
@Entity(tableName = "products")
data class ProductEntity(
    @androidx.room.PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    val category: String,
    val imageUrl: String
)