package com.example.ecommerce.ui2

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecommerce.data.Repository
import com.example.ecommerce.viewmodel.*

private const val HOME = "home"
private const val CART = "cart"
private const val RECEIPT = "receipt"
private const val ORDERS = "orders"

@Composable
fun AppNav(
    repository: Repository,
    darkMode: Boolean,
    onThemeToggle: () -> Unit
) {
    var screen by remember { mutableStateOf(HOME) }
    var selectedOrderId by remember { mutableStateOf<Long?>(null) }

    val factory = remember(repository) {
        AppViewModelFactory(repository)
    }

    val homeViewModel: HomeViewModel =
        viewModel(factory = factory)

    val cartViewModel: CartViewModel =
        viewModel(factory = factory)

    val orderViewModel: OrderViewModel =
        viewModel(factory = factory)

    val checkoutState by cartViewModel.checkoutState
        .collectAsState()

    LaunchedEffect(checkoutState.completedOrderId) {
        checkoutState.completedOrderId?.let { orderId ->
            selectedOrderId = orderId
            cartViewModel.consumeCheckoutResult()
            screen = RECEIPT
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        when (screen) {

            HOME -> HomeScreen(
                viewModel = homeViewModel,
                onCart = { screen = CART },
                onOrders = { screen = ORDERS },
                darkMode = darkMode,
                onThemeToggle = onThemeToggle
            )

            CART -> CartScreen(
                viewModel = cartViewModel,
                onBack = { screen = HOME },
                onCheckout = { cartViewModel.checkout() }
            )

            ORDERS -> OrderHistoryScreen(
                viewModel = orderViewModel,
                onBack = { screen = HOME },
                onOrderSelected = { orderId ->
                    selectedOrderId = orderId
                    screen = RECEIPT
                }
            )

            RECEIPT -> {
                val orderId = selectedOrderId

                if (orderId != null) {
                    ReceiptScreen(
                        viewModel = orderViewModel,
                        orderId = orderId,
                        onBackHome = { screen = HOME },
                        onOrders = { screen = ORDERS }
                    )
                }
            }
        }
    }
}