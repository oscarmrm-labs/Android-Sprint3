package com.qualentum.sprint3

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val latitude: Double,
    val longitude: Double,
    @SerializedName("generationtime_ms") val generationTimeMs: Double,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int,
    val timezone: String,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String,
    val elevation: Double,

    val daily: Daily,
    @SerializedName("daily_units") val dailyUnits: DailyUnits,

    val currentUnits: CurrentUnits,
    val current: CurrentWeather
)

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

data class Daily(
    val sunrise: List<String>,
    val sunset: List<String>,
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>,
    val time: List<String>
)

data class DailyUnits(
    val sunrise: String,
    val sunset: String,
    @SerializedName("temperature_2m_max") val temperatureMax: String,
    @SerializedName("temperature_2m_min") val temperatureMin: String,
    val time: String
)