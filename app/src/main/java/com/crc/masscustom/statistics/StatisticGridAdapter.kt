package com.crc.masscustom.statistics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.crc.masscustom.R

class StatisticGridAdapter(val context: Context, val statisticGridList: ArrayList<String>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.main_grv_item, null)

//        val tvGridTitle = view.findViewById<TextView>(R.id.tvGridTitle)
//
//        val strTitle = statisticGridList[position]
//
//        tvGridTitle.text = strTitle

        return view
    }

    override fun getItem(position: Int): Any {
        return statisticGridList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return statisticGridList.size
    }

}