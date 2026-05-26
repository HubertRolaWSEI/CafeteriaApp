package com.example.cafeteriaapp.data

data class PromotionItem(
    val title: String,
    val description: String,
    val validToday: Boolean,
    val code: String
)
