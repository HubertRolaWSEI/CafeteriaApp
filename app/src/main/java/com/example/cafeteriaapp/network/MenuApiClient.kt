package com.example.cafeteriaapp.network

import com.example.cafeteriaapp.data.CafeMenuItem
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class MenuApiClient {
    private val menuUrl = "http://10.0.2.2:3000/api/menu"

    fun fetchMenu(): List<CafeMenuItem> {
        val connection = URL(menuUrl).openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)

            List(jsonArray.length()) { index ->
                val item = jsonArray.getJSONObject(index)
                CafeMenuItem(
                    name = item.getString("name"),
                    description = item.getString("description"),
                    price = item.getString("price")
                )
            }
        } finally {
            connection.disconnect()
        }
    }
}