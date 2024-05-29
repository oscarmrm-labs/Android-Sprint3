package com.qualentum.sprint3.main.data.repository.remote

import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.main.data.model.nextdays.DailyForecastResponse
import com.qualentum.sprint3.main.data.model.today.CurrentDayResponse

class MainRepository (
    val latitude: Double,
    val longitude: Double,
    val currentParams: String,
    val dailyParams: String,
    val oneDay: String,
    val forecastDailyParams: String,
    val sevenDays: Int
) {
    private val apiService: MainMeteoAPIService = OpenMeteoClient.mainService

    suspend fun fetchCurrentWeatherData(): Result<CurrentDayResponse> {
        return try {
            val response = apiService.getCurrentWeather(latitude, longitude, currentParams, dailyParams, oneDay)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchDailyWeatherData(): Result<DailyForecastResponse> {
        return try {
            val response = apiService.getDaily(latitude, longitude, forecastDailyParams, sevenDays)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}