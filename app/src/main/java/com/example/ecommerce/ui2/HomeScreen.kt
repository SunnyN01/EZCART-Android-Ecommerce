package com.example.ecommerce.ui2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.ecommerce.config.AppConfig
import com.example.ecommerce.database.ProductEntity
import com.example.ecommerce.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onCart: () -> Unit,
    onOrders: () -> Unit,
    darkMode: Boolean,
    onThemeToggle: () -> Unit
) {
    val products by viewModel.filteredProducts.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val cartCount by viewModel.cartCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = AppConfig.APP_NAME,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Shop smarter. Shop better.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            TextButton(
                onClick = onOrders
            ) {
                Text("Orders")
            }

            FilledTonalIconButton(
                onClick = onThemeToggle
            ) {
                Text(
                    text = if (darkMode) "☀️" else "🌙"
                )
            }

            BadgedBox(
                badge = {
                    if (cartCount > 0) {
                        Badge {
                            Text(cartCount.toString())
                        }
                    }
                }
            ) {
                IconButton(
                    onClick = onCart
                ) {
                    Text("🛒")
                }
            }
        }

        // ---------------------------------------------------------
        // SEARCH
        // ---------------------------------------------------------

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::setSearchQuery,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            label = {
                Text("Search products")
            },
            placeholder = {
                Text("Phones, laptops, audio...")
            }
        )

        // ---------------------------------------------------------
        // CATEGORY FILTERS
        // ---------------------------------------------------------

        Spacer(Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(categories.size) { index ->

                val category = categories[index]

                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {
                        viewModel.selectCategory(category)
                    },
                    label = {
                        Text(category)
                    }
                )
            }
        }

        // ---------------------------------------------------------
        // PROMOTIONAL BANNER
        // ---------------------------------------------------------

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
                .padding(18.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "🔥 BIG TECH DEALS",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Upgrade your setup",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Great electronics. Great prices.",
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }

        // ---------------------------------------------------------
        // PRODUCT SECTION HEADER
        // ---------------------------------------------------------

        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Today's Deals",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${products.size} products",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(6.dp))

        // ---------------------------------------------------------
        // PRODUCT GRID
        // ---------------------------------------------------------

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            items(products.size) { index ->

                val product = products[index]

                ProductCard(
                    product = product,
                    onCategoryClick = {
                        viewModel.selectCategory(product.category)
                    },
                    onAddToCart = {
                        viewModel.addToCart(product)
                    }
                )
            }
        }
    }
}


// =================================================================
// PRODUCT CARD
// =================================================================

@Composable
private fun ProductCard(
    product: ProductEntity,
    onCategoryClick: () -> Unit,
    onAddToCart: () -> Unit
) {

    ElevatedCard(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            // -----------------------------------------------------
            // PRODUCT IMAGE
            // -----------------------------------------------------

            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant
                    )
            )

            Spacer(Modifier.height(8.dp))

            // -----------------------------------------------------
            // CATEGORY
            // -----------------------------------------------------

            AssistChip(
                onClick = onCategoryClick,
                label = {
                    Text(product.category)
                }
            )

            Spacer(Modifier.height(6.dp))

            // -----------------------------------------------------
            // PRODUCT NAME
            // -----------------------------------------------------

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2
            )

            // -----------------------------------------------------
            // RATING
            // -----------------------------------------------------

            Text(
                text = "★★★★★  4.7",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            // -----------------------------------------------------
            // PRICE
            // -----------------------------------------------------

            Text(
                text = "${AppConfig.CURRENCY}${"%.2f".format(product.price)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(8.dp))

            // -----------------------------------------------------
            // ADD TO CART
            // -----------------------------------------------------

            Button(
                onClick = onAddToCart,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Add to Cart")
            }
        }
    }
}