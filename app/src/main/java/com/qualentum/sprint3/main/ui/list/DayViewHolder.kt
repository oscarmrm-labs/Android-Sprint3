package com.qualentum.sprint3.main.ui.list

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qualentum.sprint3.R
import com.qualentum.sprint3.databinding.ItemDayInfoBinding
import com.qualentum.sprint3.main.data.model.nextdays.OneDay

class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemDayInfoBinding.bind(view)

    fun onBind(context: Context, dayInfo: OneDay, onClickListener: (OneDay) -> Unit) {
        binding.textView8.text = dayInfo.time
        //binding.iconDayNight.setImageDrawable(showDayNightIcon(it?.isDay))
        binding.iconWeatherState.setImageDrawable(showWeatherIcon(context, getWeatherState(dayInfo.rainSum, dayInfo.showersSum, dayInfo.snowfallSum, 0)))
        binding.textView10.text = dayInfo.temperatureMin.toString()
        binding.textView11.text = dayInfo.temperatureMax.toString()

        itemView.setOnClickListener {
            onClickListener(dayInfo)
        }
    }
    private fun showWeatherIcon(context: Context, currentWeather: String): Drawable? {
        return when (currentWeather) {
            "rain" -> ContextCompat.getDrawable(context, R.drawable.rain_svg)
            "showers" -> ContextCompat.getDrawable(context, R.drawable.showers_svg)
            "snowfall" -> ContextCompat.getDrawable(context, R.drawable.snowfall_svg)
            "cloudly" -> ContextCompat.getDrawable(context, R.drawable.cloudy_day_1_svg)
            "cloudly2" -> ContextCompat.getDrawable(context, R.drawable.cloudy_day_2_svg)
            "cloudly3" -> ContextCompat.getDrawable(context, R.drawable.cloudy_day_3_svg)
            else -> ContextCompat.getDrawable(context, R.drawable.day_svg)
        }
    }

    fun getWeatherState(rain: Double?, showers: Double?, snowfall: Double?, cloudly: Int): String {
        if (rain!! > showers!! && rain > snowfall!! ) return "rain"
        if (showers > snowfall!!) return "showers"
        if (snowfall > 0.0) return "snowfall"

        return when (cloudly) {
            in 80..100 -> "cloudly3"
            in 60..80 -> "cloudly2"
            in 40..60 -> "cloudly"
            else -> "sun"
        }

    }

}