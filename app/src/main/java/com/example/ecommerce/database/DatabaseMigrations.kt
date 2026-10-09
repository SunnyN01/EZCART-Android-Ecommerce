package com.example.ecommerce.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE products ADD COLUMN category TEXT NOT NULL DEFAULT 'Electronics'"
        )
        db.execSQL(
            "ALTER TABLE products ADD COLUMN imageUrl TEXT NOT NULL DEFAULT ''"
        )
        db.execSQL(
            "ALTER TABLE cart ADD COLUMN imageUrl TEXT NOT NULL DEFAULT ''"
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS orders (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                orderNumber TEXT NOT NULL,
                createdAt INTEGER NOT NULL,
                subtotal REAL NOT NULL,
                tax REAL NOT NULL,
                total REAL NOT NULL,
                paymentStatus TEXT NOT NULL,
                orderStatus TEXT NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            "CREATE UNIQUE INDEX IF NOT EXISTS index_orders_orderNumber ON orders(orderNumber)"
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS order_items (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                orderId INTEGER NOT NULL,
                productId INTEGER NOT NULL,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                quantity INTEGER NOT NULL,
                imageUrl TEXT NOT NULL,
                FOREIGN KEY(orderId) REFERENCES orders(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )

        db.execSQL(
            "CREATE INDEX IF NOT EXISTS index_order_items_orderId ON order_items(orderId)"
        )
    }
}