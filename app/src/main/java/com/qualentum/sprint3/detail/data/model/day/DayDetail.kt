package com.qualentum.sprint3.detail.data.model.day

import com.google.gson.annotations.SerializedName

data class DayDetailLists(
    @SerializedName("apparent_temperature_max") val apparentTemperatureMax: List<Double>,
    @SerializedName("apparent_temperature_min") val apparentTemperatureMin: List<Double>,
    @SerializedName("precipitation_hours") val precipitationHours: List<Int>,
    @SerializedName("rain_sum") val rainSum: List<Double>,
    @SerializedName("showers_sum") val showersSum: List<Double>,
    @SerializedName("snowfall_sum") val snowfallSum: List<Double>,
    val sunrise: List<String>,
    val sunset: List<String>,
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>,
    val time: List<String>,
    @SerializedName("uv_index_max") val uv_index_max: List<Double>
)


data class DailyUnits(
    @SerializedName("apparent_temperature_max") val apparentTemperatureMax: String,
    @SerializedName("apparent_temperature_min") val apparentTemperatureMin: String,
    @SerializedName("precipitation_hours") val precipitationHours: String,
    @SerializedName("rain_sum") val rainSum: String,
    @SerializedName("showers_sum") val showersSum: String,
    @SerializedName("snowfall_sum") val snowfallSum: String,
    val sunrise: String,
    val sunset: String,
    @SerializedName("temperature_2m_max") val temperatureMax: String,
    @SerializedName("temperature_2m_min") val temperatureMin: String,
    val time: String,
    @SerializedName("uv_index_max") val uvIndexMax: String
)
