package com.qualentum.sprint3.main.data.model.nextdays

data class DailyForecastResponse (
    val daily: DailyLists,
    val daily_units: DailyUnits,
    val elevation: Double,
    val generationtime_ms: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)