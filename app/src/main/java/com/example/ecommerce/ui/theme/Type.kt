package com.example.ecommerce.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(

    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    ),

    headlineMedium = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.SemiBold
    ),

    titleLarge = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),

    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),

    bodyLarge = TextStyle(
        fontSize = 15.sp
    ),

    bodyMedium = TextStyle(
        fontSize = 13.sp
    ),

    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )
)