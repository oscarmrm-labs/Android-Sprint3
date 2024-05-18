package com.qualentum.sprint3.main.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.qualentum.sprint3.databinding.ItemDayInfoBinding
import com.qualentum.sprint3.main.data.model.nextdays.OneDay

class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemDayInfoBinding.bind(view)

    fun onBind(dayInfo: OneDay, onClickListener: (OneDay) -> Unit) {
        binding.textView8.text = dayInfo.time
        binding.textView9.text = dayInfo.rainSum.toString()
        binding.textView10.text = dayInfo.temperatureMin.toString()
        binding.textView11.text = dayInfo.temperatureMax.toString()

        itemView.setOnClickListener {
            onClickListener(dayInfo)
        }
    }
}