package com.qualentum.sprint3.detail.data.repository

import com.qualentum.sprint3.detail.data.model.day.DayDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailMeteoAPIService {

    @GET("forecast")
    suspend fun getDayDetail(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
        @Query("daily") daily: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): DayDetailResponse
}