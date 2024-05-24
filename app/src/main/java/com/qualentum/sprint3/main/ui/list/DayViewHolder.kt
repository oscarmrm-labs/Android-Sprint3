package com.qualentum.sprint3.main.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.qualentum.sprint3.common.data.DEGREE_SYMBOL
import com.qualentum.sprint3.common.ui.DateFormatter
import com.qualentum.sprint3.common.ui.GetWeatherState.getWeatherIcon
import com.qualentum.sprint3.common.ui.GetWeatherState.getWeatherState
import com.qualentum.sprint3.databinding.CardDayInfoBinding
import com.qualentum.sprint3.main.data.model.nextdays.OneDay

class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = CardDayInfoBinding.bind(view)

    fun onBind(dayInfo: OneDay, onClickListener: (OneDay) -> Unit) {
        binding.textView8.text = DateFormatter.formatDay(dayInfo.time)
        binding.iconWeatherState.setImageDrawable(
            getWeatherIcon(
                binding.root.context,
                getWeatherState(dayInfo.rainSum, dayInfo.showersSum, dayInfo.snowfallSum, 0)
            )
        )
        binding.textView10.text = dayInfo.temperatureMin.toString() + DEGREE_SYMBOL
        binding.textView11.text = dayInfo.temperatureMax.toString() + DEGREE_SYMBOL

        itemView.setOnClickListener {
            onClickListener(dayInfo)
        }
    }
}