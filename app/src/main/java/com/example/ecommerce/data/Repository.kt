package com.example.ecommerce.data

import androidx.room.withTransaction
import com.example.ecommerce.config.AppConfig
import com.example.ecommerce.database.AppDatabase
import com.example.ecommerce.database.CartDao
import com.example.ecommerce.database.CartEntity
import com.example.ecommerce.database.OrderDao
import com.example.ecommerce.database.OrderEntity
import com.example.ecommerce.database.OrderWithItems
import com.example.ecommerce.database.ProductDao
import com.example.ecommerce.database.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class Repository(
    private val database: AppDatabase,
    private val productDao: ProductDao,
    private val cartDao: CartDao,
    private val orderDao: OrderDao
) {

    val products: Flow<List<ProductEntity>> =
        productDao.getProducts()

    val cart: Flow<List<CartEntity>> =
        cartDao.getCartItems()

    val orders: Flow<List<OrderEntity>> =
        orderDao.getOrders()

    val ordersWithItems: Flow<List<OrderWithItems>> =
        orderDao.getOrdersWithItems()

    suspend fun seedProducts() {
        val currentProducts = productDao.getProducts().first()

        if (
            currentProducts.isEmpty() ||
            currentProducts.any {
                it.category.isBlank() || it.imageUrl.isBlank()
            }
        ) {
            productDao.insertProducts(
                listOf(
                    ProductEntity(
                        1,
                        "iPhone 15",
                        999.0,
                        "Smartphones",
                        "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?auto=format&fit=crop&w=800&q=80"
                    ),
                    ProductEntity(
                        2,
                        "Samsung Galaxy S24",
                        899.0,
                        "Smartphones",
                        "https://images.unsplash.com/photo-1598327105666-5b89351aff97?auto=format&fit=crop&w=800&q=80"
                    ),
                    ProductEntity(
                        3,
                        "Sony Headphones",
                        199.0,
                        "Audio",
                        "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=800&q=80"
                    ),
                    ProductEntity(
                        4,
                        "MacBook Air",
                        1299.0,
                        "Laptops",
                        "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&w=800&q=80"
                    ),
                    ProductEntity(
                        5,
                        "iPad Pro",
                        599.0,
                        "Tablets",
                        "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?auto=format&fit=crop&w=800&q=80"
                    ),
                    ProductEntity(
                        6,
                        "Dell XPS 13",
                        1099.0,
                        "Laptops",
                        "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&w=800&q=80"
                    ),
                    ProductEntity(
                        7,
                        "Apple Watch",
                        399.0,
                        "Wearables",
                        "https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=800&q=80"
                    ),
                    ProductEntity(
                        8,
                        "Mechanical Keyboard",
                        99.0,
                        "Accessories",
                        "https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=800&q=80"
                    ),
                    ProductEntity(
                        9,
                        "Gaming Mouse",
                        49.0,
                        "Gaming",
                        "https://images.unsplash.com/photo-1527814050087-3793815479db?auto=format&fit=crop&w=800&q=80"
                    ),
                    ProductEntity(
                        10,
                        "Nintendo Switch",
                        349.0,
                        "Gaming",
                        "https://images.unsplash.com/photo-1578303512597-81e6cc155b3e?auto=format&fit=crop&w=800&q=80"
                    )
                )
            )
        }
    }

    suspend fun addToCart(product: ProductEntity) {
        val existing = cartDao.getCartItem(product.id)

        if (existing != null) {
            cartDao.updateCartItem(
                existing.copy(
                    quantity = existing.quantity + 1,
                    imageUrl = product.imageUrl
                )
            )
        } else {
            cartDao.insertCartItem(
                CartEntity(
                    productId = product.id,
                    name = product.name,
                    price = product.price,
                    quantity = 1,
                    imageUrl = product.imageUrl
                )
            )
        }
    }

    suspend fun removeFromCart(productId: Int) {
        val item = cartDao.getCartItem(productId) ?: return

        if (item.quantity > 1) {
            cartDao.updateCartItem(
                item.copy(quantity = item.quantity - 1)
            )
        } else {
            cartDao.deleteCartItem(item)
        }
    }

    fun getCartCount(): Flow<Int> =
        cartDao.getCartCount().map { it ?: 0 }

    fun getTotal(): Flow<Double> =
        cartDao.getCartTotal().map { it ?: 0.0 }

    suspend fun placeOrder(): Long {
        val cartItems = cartDao.getCartItemsOnce()

        require(cartItems.isNotEmpty()) {
            "Cannot place an order with an empty cart."
        }

        val subtotal = cartItems.sumOf {
            it.price * it.quantity
        }

        val tax = subtotal * AppConfig.TAX_RATE
        val total = subtotal + tax
        val timestamp = System.currentTimeMillis()

        return database.withTransaction {

            val order = OrderEntity(
                orderNumber = "EZ-$timestamp",
                createdAt = timestamp,
                subtotal = subtotal,
                tax = tax,
                total = total
            )

            val orderId = orderDao.insertOrder(order)

            orderDao.insertOrderItems(
                cartItems.map {
                    com.example.ecommerce.database.OrderItemEntity(
                        orderId = orderId,
                        productId = it.productId,
                        name = it.name,
                        price = it.price,
                        quantity = it.quantity,
                        imageUrl = it.imageUrl
                    )
                }
            )

            cartDao.clearCart()

            orderId
        }
    }

    fun observeOrder(orderId: Long): Flow<OrderWithItems?> =
        orderDao.getOrderWithItems(orderId)
}