package com.qualentum.sprint3.main.ui.list

data class DailyInfo(
    val time: List<String>,
    val temperature_2m_min: List<Double>,
    val temperature_2m_max: List<Double>,
    val rain_sum: List<Double>,
    val showers_sum: List<Double>,
    val snowfall_sum: List<Double>
)

data class OneDay(
    val time: String?,
    val temperature_2m_min: Double?,
    val temperature_2m_max: Double?,
    val rain_sum: Double?,
    val showers_sum: Double?,
    val snowfall_sum: Double?
)

data class DailyResponse(
    val daily: DailyInfo,
    val daily_units: DailyUnits,
    val elevation: Double,
    val generationtime_ms: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)

data class DailyUnits(
    val rain_sum: String,
    val showers_sum: String,
    val snowfall_sum: String,
    val temperature_2m_max: String,
    val temperature_2m_min: String,
    val time: String
)
