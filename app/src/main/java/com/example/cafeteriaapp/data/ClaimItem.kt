package com.example.cafeteriaapp.data

data class ClaimItem(
    val id: Int,
    val rewardId: Int,
    val rewardTitle: String,
    val usedStamps: Int,
    val claimedAt: String
)
