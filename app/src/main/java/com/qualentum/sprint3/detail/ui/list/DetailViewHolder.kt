package com.qualentum.sprint3.detail.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.qualentum.sprint3.databinding.CardDayDetailBinding
import com.qualentum.sprint3.detail.data.mappers.CardData

class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = CardDayDetailBinding.bind(view)

    fun onBind(cardData: CardData?) {
        binding.tvTitle.text = cardData?.title
        binding.tvDescription.text = cardData?.subTitle
        cardData?.icon?.let { binding.imageView.setImageResource(it) }
    }
}