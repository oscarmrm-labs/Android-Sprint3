package com.qualentum.sprint3.main.data.repository.remote

import com.qualentum.sprint3.main.data.model.nextdays.DailyForecastResponse
import com.qualentum.sprint3.main.data.model.today.CurrentDayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MainMeteoAPIService {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
        @Query("current") currentParams: String,
        @Query("daily") daily: String,
        @Query("forecast_days") forecastDays: Int
    ): CurrentDayResponse

    @GET("forecast")
    suspend fun getDaily(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
        @Query("daily") daily: String,
        @Query("forecast_days") forecastDays: Int,
    ): DailyForecastResponse

}