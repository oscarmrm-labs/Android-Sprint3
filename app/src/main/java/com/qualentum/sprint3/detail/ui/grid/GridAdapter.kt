package com.qualentum.sprint3.detail.ui.grid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.qualentum.sprint3.R

class GridRVAdapter(
    private val courseList: List<CardData>,
    private val context: Context
): BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var title: TextView
    private lateinit var subTitle: TextView

    override fun getCount(): Int {
        return courseList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.card_day_detail, null)
        }
        title = convertView!!.findViewById(R.id.textView14)
        subTitle = convertView!!.findViewById(R.id.textView15)
        title.setText(courseList.get(position).title)
        subTitle.setText(courseList.get(position).subTitle)
        return convertView
    }
}