package com.qualentum.sprint3.main.data.mappers

import com.qualentum.sprint3.common.data.DEGREE_SYMBOL
import com.qualentum.sprint3.common.ui.DateFormatter.formatHour
import com.qualentum.sprint3.common.ui.GetWeatherState.getWeatherState
import com.qualentum.sprint3.main.data.model.today.CurrentDayResponse

class CurrentWeatherMap {

    companion object {
        fun map(currentDayResponse: CurrentDayResponse): CurrentWeather {
            return CurrentWeather(
                temperature = currentDayResponse.current?.temperature.toString() + DEGREE_SYMBOL,
                isDay = currentDayResponse.current?.isDay,
                state = getWeatherState(
                    currentDayResponse.current?.rain,
                    currentDayResponse.current?.showers,
                    currentDayResponse.current?.snowfall,
                    0
                ),
                sunrise = formatHour(currentDayResponse.currentDay?.sunrise?.get(0)),
                sunset = formatHour(currentDayResponse.currentDay?.sunset?.get(0)),
                temperatureMax = currentDayResponse.currentDay?.temperatureMax?.get(0).toString() + DEGREE_SYMBOL,
                temperatureMin = currentDayResponse.currentDay?.temperatureMin?.get(0).toString() + DEGREE_SYMBOL,
            )
        }
    }
}

data class CurrentWeather(
    val temperature: String?,
    val isDay: Int?,
    val state: String?,
    val sunrise: String?,
    val sunset: String?,
    val temperatureMax: String?,
    val temperatureMin: String?,
)
