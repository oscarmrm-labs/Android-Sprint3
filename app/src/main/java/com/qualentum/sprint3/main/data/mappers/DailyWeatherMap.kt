package com.qualentum.sprint3.main.data.mappers

import com.qualentum.sprint3.common.data.Constants.Companion.DEGREE_SYMBOL
import com.qualentum.sprint3.common.ui.GetWeatherState
import com.qualentum.sprint3.main.data.model.nextdays.DailyForecastResponse

class DailyWeatherMap {

    companion object {
        fun map(dailyForecastResponse: DailyForecastResponse): ListDays {
            val itemsList: MutableList<OneDay> = ArrayList()
            for (i in 0..< 7) {
                itemsList.add(
                    OneDay(
                        dailyForecastResponse.dailyLists?.time?.get(i),
                        dailyForecastResponse.dailyLists?.temperatureMin?.get(i).toString() + DEGREE_SYMBOL,
                        dailyForecastResponse.dailyLists?.temperatureMax?.get(i).toString() + DEGREE_SYMBOL,
                        GetWeatherState.getWeatherState(
                            dailyForecastResponse.dailyLists?.rainSum?.get(i),
                            dailyForecastResponse.dailyLists?.showersSum?.get(i),
                            dailyForecastResponse.dailyLists?.snowfallSum?.get(i),
                            0
                        )
                    )
                )
            }
            return ListDays(itemsList)
        }
    }
}

data class OneDay(
    val time: String?,
    val temperatureMin: String?,
    val temperatureMax: String?,
    val state: String?
)

data class ListDays (
    val itemsList: MutableList<OneDay>
)