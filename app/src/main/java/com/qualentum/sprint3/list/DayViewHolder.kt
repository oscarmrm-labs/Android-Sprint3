package com.qualentum.sprint3.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.qualentum.sprint3.databinding.ItemDayInfoBinding

class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemDayInfoBinding.bind(view)

    fun onBind(dayInfo: DayInfo, onClickListener: (DayInfo) -> Unit) {
        binding.textView8.text = dayInfo.sunrise
        binding.textView9.text = dayInfo.sunset
        binding.textView10.text = dayInfo.temperatureMin
        binding.textView11.text = dayInfo.temperatureMax

        itemView.setOnClickListener {
            onClickListener(dayInfo)
        }
    }
}