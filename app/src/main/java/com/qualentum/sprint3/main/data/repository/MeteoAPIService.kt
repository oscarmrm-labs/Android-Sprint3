package com.qualentum.sprint3.main.data.repository

import com.qualentum.sprint3.detail.data.model.day.DayDetailResponse
import com.qualentum.sprint3.main.data.model.nextdays.DailyForecastResponse
import com.qualentum.sprint3.main.data.model.today.CurrentDayResponse
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
    ): Call<CurrentDayResponse>

    @GET("forecast")
    fun getDaily(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
        @Query("daily") daily: String,
        @Query("forecast_days") forecastDays: Int,
    ): Call<DailyForecastResponse>

    @GET("forecast")
    fun getDayDetail(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
        @Query("daily") daily: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Call<DayDetailResponse>
}