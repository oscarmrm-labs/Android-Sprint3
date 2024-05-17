package com.qualentum.sprint3.detail.data.model.day

data class DayDetailResponse(
    val daily: DayDetailLists,
    val daily_units: DailyUnits,
    val elevation: Int,
    val generationtime_ms: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)
