EZCART – Android E-Commerce Application

EZCART is an Android shopping application built with Kotlin and Jetpack Compose. It provides product discovery, search, category filtering, cart management, checkout, and order history with persistent local storage.

Features
Product Catalog: Browse products with images, categories, and prices.
Search and Filtering: Find products by name and filter by category.
Shopping Cart: Add products, update quantities, and remove items.
Checkout: Review order totals and complete a simulated checkout flow.
Order History: Store orders and order items locally and view previous purchases.
Receipts: Display order details and purchase summaries.
Offline Persistence: Store products, cart items, and orders using Room and SQLite.
Modern UI: Use Jetpack Compose and Material 3 with light and dark themes.
Technology Stack
Language: Kotlin
UI: Jetpack Compose, Material 3
Architecture: Model–View–ViewModel (MVVM)
Database: Room, SQLite
Asynchronous Programming: Kotlin Coroutines and Flow
Product Images: Coil
Development Environment: Android Studio
Architecture

The application separates its responsibilities into layers:

UI: Compose screens for the home page, cart, checkout, receipts, and order history.
ViewModel: Manages screen state and coordinates UI actions.
Repository: Provides access to application data.
Database: Room entities and DAOs persist products, cart items, orders, and order items.
Getting Started
Clone this repository.
Open the project in Android Studio.
Allow Gradle to sync.
Run the app on an Android emulator or compatible device.
Checkout Note

Checkout uses a simulated payment flow. A real payment gateway is not currently integrated.

Future Improvements
Integrate a real payment provider.
Add automated unit and UI tests.
Improve product data sourcing and catalog management.
Author

Developed as an Android application project demonstrating Kotlin, Jetpack Compose, MVVM, and local database persistence.