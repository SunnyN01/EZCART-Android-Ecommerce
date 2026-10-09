package com.example.ecommerce.ui2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecommerce.config.AppConfig
import com.example.ecommerce.viewmodel.OrderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderHistoryScreen(
    viewModel: OrderViewModel,
    onBack: () -> Unit,
    onOrderSelected: (Long) -> Unit
) {
    val orders by viewModel.orders.collectAsState()

    val formatter = remember {
        SimpleDateFormat(
            "MMM dd, yyyy • h:mm a",
            Locale.getDefault()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "My Orders",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            OutlinedButton(onClick = onBack) {
                Text("Shop")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment =
                    androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    "No orders yet",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        } else {
            LazyColumn(
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {
                items(
                    orders,
                    key = { it.order.id }
                ) { orderWithItems ->

                    val order = orderWithItems.order

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            onOrderSelected(order.id)
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    order.orderNumber,
                                    modifier = Modifier.weight(1f),
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    "${AppConfig.CURRENCY}${"%.2f".format(order.total)}",
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }

                            Spacer(Modifier.height(6.dp))

                            Text(
                                formatter.format(
                                    Date(order.createdAt)
                                ),
                                color =
                                    MaterialTheme.colorScheme
                                        .onSurfaceVariant
                            )

                            Text(
                                "${orderWithItems.items.size} products • ${order.orderStatus}",
                                color =
                                    MaterialTheme.colorScheme
                                        .tertiary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}