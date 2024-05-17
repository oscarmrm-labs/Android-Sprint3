package com.qualentum.sprint3.main.data.model.today

import com.google.gson.annotations.SerializedName

data class CurrentDay(
    val sunrise: List<String>,
    val sunset: List<String>,
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>,
    val time: List<String>
)

data class CurrentDayUnits(
    val sunrise: String,
    val sunset: String,
    @SerializedName("temperature_2m_max") val temperatureMax: String,
    @SerializedName("temperature_2m_min") val temperatureMin: String,
    val time: String
)