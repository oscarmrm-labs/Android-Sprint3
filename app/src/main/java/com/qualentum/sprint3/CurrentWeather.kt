package com.qualentum.sprint3

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("generationtime_ms") val generationTimeMs: Double,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String,
    @SerializedName("elevation") val elevation: Double,
    @SerializedName("current_units") val currentUnits: CurrentUnits,
    @SerializedName("current") val current: CurrentWeather
)

data class CurrentUnits(
    @SerializedName("time") val time: String,
    @SerializedName("interval") val interval: String,
    @SerializedName("temperature_2m") val temperature2m: String,
    @SerializedName("is_day") val isDay: String,
    @SerializedName("rain") val rain: String,
    @SerializedName("showers") val showers: String,
    @SerializedName("snowfall") val snowfall: String
)

data class CurrentWeather(
    @SerializedName("time") val time: String,
    @SerializedName("interval") val interval: Int,
    @SerializedName("temperature_2m") val temperature2m: Double,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("rain") val rain: Double,
    @SerializedName("showers") val showers: Double,
    @SerializedName("snowfall") val snowfall: Double
)
