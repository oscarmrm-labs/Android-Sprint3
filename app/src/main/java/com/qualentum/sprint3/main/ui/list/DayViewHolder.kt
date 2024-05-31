package com.qualentum.sprint3.main.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.qualentum.sprint3.common.ui.DateFormatter
import com.qualentum.sprint3.common.ui.GetWeatherState.getWeatherIcon
import com.qualentum.sprint3.databinding.CardDayInfoBinding
import com.qualentum.sprint3.main.data.mappers.OneDay

class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = CardDayInfoBinding.bind(view)

    fun onBind(dayInfo: OneDay?, onClickListener: (OneDay?) -> Unit) {
        binding.textView8.text = DateFormatter.formatDay(dayInfo?.time)
        binding.iconWeatherState.setImageDrawable(
            getWeatherIcon(
                binding.root.context,
                dayInfo?.state
            )
        )
        binding.textView10.text = dayInfo?.temperatureMin
        binding.textView11.text = dayInfo?.temperatureMax

        itemView.setOnClickListener {
            onClickListener(dayInfo)
        }
    }
}