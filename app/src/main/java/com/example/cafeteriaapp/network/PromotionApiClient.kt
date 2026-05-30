package com.example.cafeteriaapp.network

import com.example.cafeteriaapp.data.PromotionItem
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class PromotionApiClient {
    private val promotionUrl = "http://10.0.2.2:3000/api/promotion"

    fun fetchPromotion(): PromotionItem {
        val connection = URL(promotionUrl).openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if (connection.responseCode !in 200..299) {
                throw IllegalStateException("Backend returned ${connection.responseCode}")
            }

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val json = JSONObject(response)

            PromotionItem(
                title = json.getString("title"),
                description = json.getString("description"),
                validToday = json.optBoolean("validToday", true),
                code = json.optString("code", "")
            )
        } finally {
            connection.disconnect()
        }
    }
}
