package com.qualentum.sprint3.detail.data.model.day

import com.google.gson.annotations.SerializedName

data class DayDetailResponse(
    @SerializedName("daily") val dayDetailLists: DayDetailLists,
    @SerializedName("daily_units") val dailyUnits: DailyUnits,
    val elevation: Int,
    @SerializedName("generationtime_ms") val generationtimeMs: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int
)
