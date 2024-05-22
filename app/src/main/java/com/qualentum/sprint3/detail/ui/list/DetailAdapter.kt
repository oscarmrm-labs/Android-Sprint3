package com.qualentum.sprint3.detail.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qualentum.sprint3.R
import com.qualentum.sprint3.detail.data.model.CardData

class DetailAdapter(val itemList: List<CardData>):
    RecyclerView.Adapter<DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DetailViewHolder(layoutInflater.inflate(R.layout.card_day_detail, parent, false))
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = itemList.get(position)
        holder.onBind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}