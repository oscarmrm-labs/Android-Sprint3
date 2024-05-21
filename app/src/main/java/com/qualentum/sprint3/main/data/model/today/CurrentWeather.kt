package com.qualentum.sprint3.main.data.model.today

import com.google.gson.annotations.SerializedName

data class CurrentUnits(
    val time: String,
    val interval: String,
    @SerializedName("temperature_2m") val temperature: String,
    @SerializedName("is_day") val isDay: String,
    val rain: String,
    val showers: String,
    val snowfall: String
)

data class CurrentWeather(
    val time: String,
    val interval: Int,
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("is_day") val isDay: Int,
    val rain: Double,
    val showers: Double,
    val snowfall: Double
)
