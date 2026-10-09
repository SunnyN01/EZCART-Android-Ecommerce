package com.example.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.ecommerce.data.Repository
import com.example.ecommerce.database.AppDatabase
import com.example.ecommerce.database.MIGRATION_1_2
import com.example.ecommerce.ui.theme.ECommerceAppTheme
import com.example.ecommerce.ui2.AppNav
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var database: AppDatabase
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "ecommerce_database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()

        repository = Repository(
            database = database,
            productDao = database.productDao(),
            cartDao = database.cartDao(),
            orderDao = database.orderDao()
        )

        lifecycleScope.launch {
            repository.seedProducts()
        }

        setContent {

            val systemDarkTheme =
                isSystemInDarkTheme()

            var darkMode by remember {
                mutableStateOf(systemDarkTheme)
            }

            ECommerceAppTheme(
                darkTheme = darkMode
            ) {

                AppNav(
                    repository = repository,
                    darkMode = darkMode,
                    onThemeToggle = {
                        darkMode = !darkMode
                    }
                )
            }
        }
    }
}