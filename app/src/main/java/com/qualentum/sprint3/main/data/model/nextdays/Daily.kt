package com.qualentum.sprint3.main.data.model.nextdays

import com.google.gson.annotations.SerializedName

data class OneDay(
    val time: String?,
    @SerializedName("temperature_2m_min") val temperatureMin: Double?,
    @SerializedName("temperature_2m_max") val temperatureMax: Double?,
    @SerializedName("rain_sum") val rainSum: Double?,
    @SerializedName("showers_sum") val showersSum: Double?,
    @SerializedName("snowfall_sum") val snowfallSum: Double?
)

data class DailyLists(
    val time: List<String>,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>,
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerializedName("rain_sum") val rainSum: List<Double>,
    @SerializedName("showers_sum") val showersSum: List<Double>,
    @SerializedName("snowfall_sum") val snowfallSum: List<Double>
)

data class DailyUnits(
    @SerializedName("rain_sum") val rainSum: String,
    @SerializedName("showers_sum") val showersSum: String,
    @SerializedName("snowfall_sum") val snowfallSum: String,
    @SerializedName("temperature_2m_max") val temperatureMax: String,
    @SerializedName("temperature_2m_min") val temperatureMin: String,
    val time: String
)
