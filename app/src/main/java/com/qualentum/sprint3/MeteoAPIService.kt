package com.qualentum.sprint3

import com.qualentum.sprint3.detail.ui.grid.DayDetail
import com.qualentum.sprint3.main.ui.list.DailyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MeteoAPIService {

    @GET("forecast")
    fun getWeather(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
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

    @GET("forecast")
    fun getDayDetail(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
        @Query("daily") daily: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Call<DayDetail>
}