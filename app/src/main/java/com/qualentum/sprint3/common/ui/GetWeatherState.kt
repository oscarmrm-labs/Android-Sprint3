package com.qualentum.sprint3.common.ui

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.qualentum.sprint3.R

object GetWeatherState {
    fun getWeatherState(rain: Double?, showers: Double?, snowfall: Double?, cloudy: Int?): String {
        if (rain!! > showers!! && rain > snowfall!! ) return WeatherStateEnum.RAIN.state
        if (showers > snowfall!!) return WeatherStateEnum.SHOWERS.state
        if (snowfall > 0.0) return WeatherStateEnum.SNOWFALL.state

        return when (cloudy) {
            in 80..100 -> WeatherStateEnum.HIGH_CLOUDY.state
            in 60..80 -> WeatherStateEnum.CLOUDY.state
            in 40..60 -> WeatherStateEnum.PARTLY_CLOUDY.state
            else -> WeatherStateEnum.SUN.state
        }
    }

    fun getWeatherIcon(context: Context, currentWeather: String): Drawable? {
        return when (currentWeather) {
            WeatherStateEnum.RAIN.state -> ContextCompat.getDrawable(context, R.drawable.rain_svg)
            WeatherStateEnum.SHOWERS.state -> ContextCompat.getDrawable(context, R.drawable.showers_svg)
            WeatherStateEnum.SNOWFALL.state -> ContextCompat.getDrawable(context, R.drawable.snowfall_svg)
            WeatherStateEnum.PARTLY_CLOUDY.state -> ContextCompat.getDrawable(context, R.drawable.cloudy_day_1_svg)
            WeatherStateEnum.CLOUDY.state -> ContextCompat.getDrawable(context, R.drawable.cloudy_day_2_svg)
            WeatherStateEnum.HIGH_CLOUDY.state -> ContextCompat.getDrawable(context, R.drawable.cloudy_day_3_svg)
            else -> ContextCompat.getDrawable(context, R.drawable.day_svg)
        }
    }

    fun getWeatherDescription(context: Context, currentWeather: String): String? {
        return when (currentWeather) {
            WeatherStateEnum.RAIN.state -> context.getString(R.string.weather_state_description_rain)
            WeatherStateEnum.SHOWERS.state -> context.getString(R.string.weather_state_description_showers)
            WeatherStateEnum.SNOWFALL.state -> context.getString(R.string.weather_state_description_snowfall)
            WeatherStateEnum.PARTLY_CLOUDY.state -> context.getString(R.string.weather_state_description_partly_cloudy)
            WeatherStateEnum.CLOUDY.state -> context.getString(R.string.weather_state_description_cloudy)
            WeatherStateEnum.HIGH_CLOUDY.state -> context.getString(R.string.weather_state_description_highly_cloudy)
            else -> context.getString(R.string.weather_state_description_sun)
        }
    }
}