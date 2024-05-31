package com.qualentum.sprint3.main.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qualentum.sprint3.R
import com.qualentum.sprint3.main.data.mappers.OneDay

class DayAdapter(val itemList: MutableList<OneDay>?, private val onClickListener: (OneDay?) -> Unit) :
    RecyclerView.Adapter<DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DayViewHolder(layoutInflater.inflate(R.layout.card_day_info, parent, false))
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = itemList?.get(position)
        holder.onBind(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

}