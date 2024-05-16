package com.qualentum.sprint3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MeteoAPIService {

    @GET("forecast")
    fun getWeather(
        @Query("latitude") latitud: String,
        @Query("longitude") longitud: String,
        @Query("current") currentParams: String
    ): Call<ForecastResponse>

}