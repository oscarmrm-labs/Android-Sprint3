package com.qualentum.sprint3.detail.data.repository.remote

import com.qualentum.sprint3.detail.data.mappers.DetailWeather
import com.qualentum.sprint3.detail.data.mappers.DetailWeatherMap

class DetailRepository(
    private val apiService: DetailMeteoAPIService,
    private val day: String,
    private val latitude: Double,
    private val longitude: Double,
) {

    suspend fun fetchDetailData2(): Result<DetailWeather> {
        return try {
            val paramsDaily = "temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,uv_index_max,rain_sum,showers_sum,snowfall_sum"
            val response = apiService.getDayDetail(latitude, longitude, paramsDaily, day, day)
            val responseMapped = DetailWeatherMap.map(response)
            Result.success(responseMapped)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}