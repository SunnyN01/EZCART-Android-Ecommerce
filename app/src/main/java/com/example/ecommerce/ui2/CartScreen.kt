package com.example.ecommerce.ui2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.ecommerce.config.AppConfig
import com.example.ecommerce.viewmodel.CartViewModel

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    onBack: () -> Unit,
    onCheckout: () -> Unit
) {
    val cart by viewModel.cart.collectAsState()
    val total by viewModel.total.collectAsState()
    val checkoutState by viewModel.checkoutState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Shopping Cart",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(onClick = onBack) {
            Text("← Continue Shopping")
        }

        Spacer(Modifier.height(12.dp))

        if (cart.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Your cart is empty",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(cart, key = { it.productId }) { item ->

                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = item.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                            )

                            Spacer(Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    item.name,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    "${AppConfig.CURRENCY}${"%.2f".format(item.price)}"
                                )

                                Row(
                                    horizontalArrangement =
                                        Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            viewModel.remove(
                                                item.productId
                                            )
                                        }
                                    ) {
                                        Text("−")
                                    }

                                    Text(
                                        "Qty: ${item.quantity}",
                                        modifier = Modifier.padding(
                                            horizontal = 5.dp,
                                            vertical = 10.dp
                                        ),
                                        fontWeight = FontWeight.Bold
                                    )

                                    Button(
                                        onClick = {
                                            viewModel.add(
                                                com.example.ecommerce.database.ProductEntity(
                                                    item.productId,
                                                    item.name,
                                                    item.price,
                                                    "Electronics",
                                                    item.imageUrl
                                                )
                                            )
                                        }
                                    ) {
                                        Text("+")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        HorizontalDivider()

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Total",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                "${AppConfig.CURRENCY}${"%.2f".format(total)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(12.dp))

        checkoutState.error?.let {
            Text(
                it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = onCheckout,
            modifier = Modifier.fillMaxWidth(),
            enabled = cart.isNotEmpty() &&
                    !checkoutState.isCheckingOut,
            shape = RoundedCornerShape(12.dp)
        ) {
            if (checkoutState.isCheckingOut) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Place Order")
            }
        }
    }
}