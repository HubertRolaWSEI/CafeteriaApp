package com.example.cafeteriaapp.network

import com.example.cafeteriaapp.data.ClaimItem
import com.example.cafeteriaapp.data.RewardItem
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ClaimsApiClient {
    private val claimsUrl = "http://10.0.2.2:3000/api/claims"

    fun fetchClaims(): List<ClaimItem> {
        val connection = URL(claimsUrl).openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if (connection.responseCode !in 200..299) {
                throw IllegalStateException("Backend returned ${connection.responseCode}")
            }

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)

            List(jsonArray.length()) { index ->
                val item = jsonArray.getJSONObject(index)
                ClaimItem(
                    id = item.getInt("id"),
                    rewardId = item.getInt("rewardId"),
                    rewardTitle = item.getString("rewardTitle"),
                    usedStamps = item.getInt("usedStamps"),
                    claimedAt = item.getString("claimedAt")
                )
            }
        } finally {
            connection.disconnect()
        }
    }

    fun createClaim(reward: RewardItem): ClaimItem {
        val connection = URL(claimsUrl).openConnection() as HttpURLConnection

        return try {
            val requestBody = JSONObject()
                .put("rewardId", reward.id)
                .put("rewardTitle", reward.title)
                .put("usedStamps", reward.requiredStamps)
                .toString()

            connection.requestMethod = "POST"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")

            connection.outputStream.use { output ->
                output.write(requestBody.toByteArray())
            }

            if (connection.responseCode !in 200..299) {
                throw IllegalStateException("Backend returned ${connection.responseCode}")
            }

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val item = JSONObject(response)

            ClaimItem(
                id = item.getInt("id"),
                rewardId = item.getInt("rewardId"),
                rewardTitle = item.getString("rewardTitle"),
                usedStamps = item.getInt("usedStamps"),
                claimedAt = item.getString("claimedAt")
            )
        } finally {
            connection.disconnect()
        }
    }
}
