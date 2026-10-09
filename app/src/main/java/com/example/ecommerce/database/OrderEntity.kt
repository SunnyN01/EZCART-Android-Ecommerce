package com.example.ecommerce.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "orders",
    indices = [Index(value = ["orderNumber"], unique = true)]
)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String,
    val createdAt: Long,
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val paymentStatus: String = "PAID",
    val orderStatus: String = "CONFIRMED"
)