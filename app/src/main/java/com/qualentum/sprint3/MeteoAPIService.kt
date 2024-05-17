package com.qualentum.sprint3

import com.qualentum.sprint3.ui.main.list.DailyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MeteoAPIService {

    @GET("forecast")
    fun getWeather(
        @Query("latitude") latitud: String,
        @Query("longitude") longitud: String,
        @Query("current") currentParams: String,
        @Query("daily") daily: String,
        @Query("forecast_days") forecastDays: String
    ): Call<ForecastResponse>

    @GET("forecast")
    fun getDaily(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
        @Query("daily") daily: String,
        @Query("forecast_days") forecastDays: Int,
    ): Call<DailyResponse>
}