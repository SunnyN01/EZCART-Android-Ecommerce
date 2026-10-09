package com.example.ecommerce.ui2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun ReceiptScreen(
    viewModel: OrderViewModel,
    orderId: Long,
    onBackHome: () -> Unit,
    onOrders: () -> Unit
) {
    val orderWithItems by viewModel
        .observeOrder(orderId)
        .collectAsState(initial = null)

    val formatter = remember {
        SimpleDateFormat(
            "MMM dd, yyyy • h:mm a",
            Locale.getDefault()
        )
    }

    val order = orderWithItems?.order

    if (order == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment =
                androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            "✅ Order Confirmed",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(6.dp))

        Text(
            "Thank you for shopping with ${AppConfig.APP_NAME}",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(18.dp))

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.padding(18.dp)
            ) {

                item {
                    Text(
                        order.orderNumber,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        formatter.format(Date(order.createdAt)),
                        color =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant
                    )

                    Spacer(Modifier.height(14.dp))
                }

                items(
                    orderWithItems?.items.orEmpty()
                ) { item ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${item.name} × ${item.quantity}",
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            "${AppConfig.CURRENCY}${"%.2f".format(item.price * item.quantity)}"
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                }

                item {
                    HorizontalDivider()

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Subtotal: ${AppConfig.CURRENCY}${"%.2f".format(order.subtotal)}"
                    )

                    Text(
                        "Tax: ${AppConfig.CURRENCY}${"%.2f".format(order.tax)}"
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "Total Paid: ${AppConfig.CURRENCY}${"%.2f".format(order.total)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "Payment: ${order.paymentStatus}",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        "Status: ${order.orderStatus}",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onOrders,
                modifier = Modifier.weight(1f)
            ) {
                Text("Order History")
            }

            Button(
                onClick = onBackHome,
                modifier = Modifier.weight(1f)
            ) {
                Text("Continue Shopping")
            }
        }
    }
}