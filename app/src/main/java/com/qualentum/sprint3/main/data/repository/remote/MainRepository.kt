package com.qualentum.sprint3.main.data.repository.remote

import com.qualentum.sprint3.main.data.model.nextdays.DailyForecastResponse
import com.qualentum.sprint3.main.data.model.today.CurrentDayResponse

class MainRepository (
    private val apiService: MainMeteoAPIService,
    private val latitude: Double,
    private val longitude: Double,
) {
    suspend fun fetchCurrentWeatherData(): Result<CurrentDayResponse> {
        return try {
            val currentParams = "temperature_2m,is_day,rain,showers,snowfall"
            val dailyParams = "temperature_2m_max,temperature_2m_min,sunrise,sunset"
            val oneDay =  1
            val response = apiService.getCurrentWeather(latitude, longitude, currentParams, dailyParams, oneDay)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchDailyWeatherData(): Result<DailyForecastResponse> {
        return try {
            val forecastDailyParams = "temperature_2m_max,temperature_2m_min,rain_sum,showers_sum,snowfall_sum"
            val sevenDays = 7
            val response = apiService.getDaily(latitude, longitude, forecastDailyParams, sevenDays)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}