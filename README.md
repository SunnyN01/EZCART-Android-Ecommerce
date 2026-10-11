# EZCART – Android E-Commerce Application

EZCART is a native Android shopping application built with **Kotlin, Jetpack Compose, MVVM, and Room**. It demonstrates end-to-end shopping workflows, reactive UI development, and persistent local data storage.

## Features

- **Product Catalog:** Browse products with images, categories, and prices.
- **Search and Filtering:** Search products by name and filter by category.
- **Shopping Cart:** Add products, update quantities, and remove items.
- **Checkout:** Review order details and totals through a simulated checkout flow.
- **Order History:** Persist orders and associated order items and view previous purchases.
- **Receipts:** Display order details and purchase summaries after checkout.
- **Offline Persistence:** Store product, cart, and order data locally using Room and SQLite.
- **Modern UI:** Build responsive shopping screens with Jetpack Compose and Material 3, including light and dark themes.

## Technology Stack

| Category | Technologies |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM, Repository Pattern |
| Database | Room, SQLite |
| Asynchronous Programming | Kotlin Coroutines, Flow |
| Image Loading | Coil |
| Development Tools | Android Studio, Gradle |

## Architecture

The application separates UI, business logic, and data persistence into distinct layers:

- **UI Layer:** Compose screens for product browsing, cart management, checkout, receipts, and order history.
- **ViewModel Layer:** Manages screen state and coordinates user actions using observable UI state.
- **Repository Layer:** Centralizes access to product, cart, and order data.
- **Database Layer:** Uses Room entities and DAOs to persist products, cart items, orders, and order items in a local SQLite database.

This structure improves separation of concerns and makes the application easier to maintain and extend.

## Getting Started

1. Clone this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and resolve dependencies.
4. Run the application on an Android emulator or compatible Android device.

## Checkout and Payments

Checkout currently uses a **simulated payment flow**. A production payment gateway is not integrated.

## Future Improvements

- Integrate a payment provider.
- Add automated unit and UI tests.
- Improve product data sourcing and catalog management.
- Explore remote API integration while retaining local persistence.

## Author

Developed as an Android application project demonstrating native Android development, modern UI architecture, and local database persistence.
