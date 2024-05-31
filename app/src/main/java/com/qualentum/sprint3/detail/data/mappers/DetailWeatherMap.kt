package com.qualentum.sprint3.detail.data.mappers

import com.qualentum.sprint3.R
import com.qualentum.sprint3.common.data.Constants.Companion.DEGREE_SYMBOL
import com.qualentum.sprint3.common.ui.DateFormatter
import com.qualentum.sprint3.detail.data.model.day.DayDetailResponse

class DetailWeatherMap {
    companion object {
        fun map(dayDetailResponse: DayDetailResponse): DetailWeather {
            val itemsList: MutableList<CardData> = ArrayList()
            itemsList.add(CardData("Min. Temp.", dayDetailResponse.dayDetailLists.temperatureMin[0].toString() + DEGREE_SYMBOL, (R.drawable.thermometer_cold_svg)))
            itemsList.add(CardData("Max. Temp.", dayDetailResponse.dayDetailLists.temperatureMax[0].toString() + DEGREE_SYMBOL, (R.drawable.thermometer_hot_svg)))
            itemsList.add(CardData("Sensación Min.", dayDetailResponse.dayDetailLists.apparentTemperatureMin[0].toString() + DEGREE_SYMBOL, (R.drawable.thermometer_cold_svg)))
            itemsList.add(CardData("Sensación Max.", dayDetailResponse.dayDetailLists.apparentTemperatureMax[0].toString() + DEGREE_SYMBOL, (R.drawable.thermometer_hot_svg)))
            itemsList.add(CardData("Amanecer", DateFormatter.formatHour(dayDetailResponse.dayDetailLists.sunrise[0]), (R.drawable.sunrise_svg)))
            itemsList.add(CardData("Anochecer", DateFormatter.formatHour(dayDetailResponse.dayDetailLists.sunset[0]), (R.drawable.sunset_svg)))
            itemsList.add(CardData("UV Max.", dayDetailResponse.dayDetailLists.uv_index_max[0].toString(), (R.drawable.uv_ray_svg)))
            itemsList.add(CardData("Prob. lluvia", dayDetailResponse.dayDetailLists.rainSum[0].toString(), (R.drawable.umbrella_svg)))
            itemsList.add(CardData("Prob. chubascos", dayDetailResponse.dayDetailLists.showersSum[0].toString(), (R.drawable.umbrella_2_svg)))
            itemsList.add(CardData("Prob. nevadas", dayDetailResponse.dayDetailLists.snowfallSum[0].toString(), (R.drawable.snowflake_svg)))
            return DetailWeather(itemsList)
        }
    }
}

data class CardData(
    val title: String?,
    val subTitle: String?,
    val icon: Int?
)

data class DetailWeather (
    val itemsList: MutableList<CardData>
)