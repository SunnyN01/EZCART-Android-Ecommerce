package com.example.ecommerce.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartEntity)

    @Update
    suspend fun updateCartItem(item: CartEntity)

    @Delete
    suspend fun deleteCartItem(item: CartEntity)

    @Query("SELECT * FROM cart")
    fun getCartItems(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart")
    suspend fun getCartItemsOnce(): List<CartEntity>

    @Query("SELECT * FROM cart WHERE productId = :productId LIMIT 1")
    suspend fun getCartItem(productId: Int): CartEntity?

    @Query("DELETE FROM cart")
    suspend fun clearCart()

    @Query("SELECT SUM(quantity) FROM cart")
    fun getCartCount(): Flow<Int?>

    @Query("SELECT SUM(price * quantity) FROM cart")
    fun getCartTotal(): Flow<Double?>
}