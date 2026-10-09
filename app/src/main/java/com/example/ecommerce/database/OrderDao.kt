package com.example.ecommerce.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    fun getOrders(): Flow<List<OrderEntity>>

    @Transaction
    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    fun getOrdersWithItems(): Flow<List<OrderWithItems>>

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId LIMIT 1")
    fun getOrderWithItems(orderId: Long): Flow<OrderWithItems?>
}