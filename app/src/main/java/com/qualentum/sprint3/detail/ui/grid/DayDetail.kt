package com.qualentum.sprint3.detail.ui.grid

data class Daily2(
    val apparent_temperature_max: List<Double>,
    val apparent_temperature_min: List<Double>,
    val precipitation_hours: List<Int>,
    val rain_sum: List<Int>,
    val showers_sum: List<Int>,
    val snowfall_sum: List<Int>,
    val sunrise: List<String>,
    val sunset: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<String>,
    val uv_index_max: List<Double>
)

data class DayDetail(
    val daily: Daily2,
    val daily_units: DailyUnits,
    val elevation: Int,
    val generationtime_ms: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)

data class DailyUnits(
    val apparent_temperature_max: String,
    val apparent_temperature_min: String,
    val precipitation_hours: String,
    val rain_sum: String,
    val showers_sum: String,
    val snowfall_sum: String,
    val sunrise: String,
    val sunset: String,
    val temperature_2m_max: String,
    val temperature_2m_min: String,
    val time: String,
    val uv_index_max: String
)
