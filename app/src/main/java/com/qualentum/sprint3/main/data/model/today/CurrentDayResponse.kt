package com.qualentum.sprint3.main.data.model.today

import com.google.gson.annotations.SerializedName

data class CurrentDayResponse(
    val latitude: Double,
    val longitude: Double,
    @SerializedName("generationtime_ms") val generationTimeMs: Double,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int,
    val timezone: String,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String,
    val elevation: Double,
    @SerializedName("daily") val currentDay: CurrentDay?,
    @SerializedName("daily_units") val currentDayUnits: CurrentDayUnits?,
    val currentUnits: CurrentUnits?,
    val current: CurrentWeather?
)
