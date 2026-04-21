package com.example.geminikmp_omae

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class WeatherResponse(
    val current_weather: CurrentWeather
)

@Serializable
data class CurrentWeather(
    val temperature: Double,
    val windspeed: Double,
    val weathercode: Int
)

class WeatherApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun fetchTokyoWeather(): String {
        return try {
            // 東京の座標をハッキング！
            val response: WeatherResponse = client.get("https://api.open-meteo.com/v1/forecast?latitude=35.6895&longitude=139.6917&current_weather=true").body()
            "TEMP: ${response.current_weather.temperature}°C / WIND: ${response.current_weather.windspeed}km/h"
        } catch (e: Exception) {
            "ERROR: SIGNAL INTERRUPTED - ${e.message}"
        }
    }
}
