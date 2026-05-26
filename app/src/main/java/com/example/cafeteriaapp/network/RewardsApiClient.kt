package com.example.cafeteriaapp.network

import com.example.cafeteriaapp.data.RewardItem
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class RewardsApiClient {
    private val rewardsUrl = "http://10.0.2.2:3000/api/rewards"

    fun fetchRewards(): List<RewardItem> {
        val connection = URL(rewardsUrl).openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)

            List(jsonArray.length()) { index ->
                val item = jsonArray.getJSONObject(index)
                RewardItem(
                    id = item.getInt("id"),
                    title = item.getString("title"),
                    description = item.getString("description"),
                    requiredStamps = item.getInt("requiredStamps"),
                    status = item.getString("status")
                )
            }
        } finally {
            connection.disconnect()
        }
    }
}