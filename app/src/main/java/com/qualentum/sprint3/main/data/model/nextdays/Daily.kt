package com.qualentum.sprint3.main.data.model.nextdays

data class OneDay(
    val time: String?,
    val temperature_2m_min: Double?,
    val temperature_2m_max: Double?,
    val rain_sum: Double?,
    val showers_sum: Double?,
    val snowfall_sum: Double?
)

data class DailyLists(
    val time: List<String>,
    val temperature_2m_min: List<Double>,
    val temperature_2m_max: List<Double>,
    val rain_sum: List<Double>,
    val showers_sum: List<Double>,
    val snowfall_sum: List<Double>
)

data class DailyUnits(
    val rain_sum: String,
    val showers_sum: String,
    val snowfall_sum: String,
    val temperature_2m_max: String,
    val temperature_2m_min: String,
    val time: String
)
