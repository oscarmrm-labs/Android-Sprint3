package com.qualentum.sprint3.main.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qualentum.sprint3.R
import com.qualentum.sprint3.main.data.model.nextdays.OneDay

class DayAdapter(val dailyInfo: List<OneDay>, private val onClickListener: (OneDay) -> Unit) :
    RecyclerView.Adapter<DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DayViewHolder(layoutInflater.inflate(R.layout.item_day_info, parent, false))
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = dailyInfo[position]
        holder.onBind(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return dailyInfo.size
    }
}