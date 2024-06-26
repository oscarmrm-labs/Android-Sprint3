package com.qualentum.sprint3.main.data.repository.remote

import com.qualentum.sprint3.main.data.mappers.CurrentWeather
import com.qualentum.sprint3.main.data.mappers.CurrentWeatherMap
import com.qualentum.sprint3.main.data.mappers.DailyWeatherMap
import com.qualentum.sprint3.main.data.mappers.ListDays
import javax.inject.Inject

class MainRepository @Inject constructor (
    private val apiService: MainMeteoAPIService,
    private val latitude: Double,
    private val longitude: Double,
) {
    suspend fun fetchCurrentWeatherData(): Result<CurrentWeather> {
        return try {
            val currentParams = "temperature_2m,is_day,rain,showers,snowfall"
            val dailyParams = "temperature_2m_max,temperature_2m_min,sunrise,sunset"
            val oneDay =  1
            val response = apiService.getCurrentWeather(latitude, longitude, currentParams, dailyParams, oneDay)
            val currentData = CurrentWeatherMap.map(response)
            Result.success(currentData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchDailyWeatherData(): Result<ListDays> {
        return try {
            val forecastDailyParams = "temperature_2m_max,temperature_2m_min,rain_sum,showers_sum,snowfall_sum"
            val sevenDays = 7
            val response = apiService.getDaily(latitude, longitude, forecastDailyParams, sevenDays)
            val forecastData = DailyWeatherMap.map(response)
            Result.success(forecastData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}