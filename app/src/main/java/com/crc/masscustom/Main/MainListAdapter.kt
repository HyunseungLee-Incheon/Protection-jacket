package com.crc.masscustom.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.crc.masscustom.R

class MainListAdapter (val context: Context, val mainList: ArrayList<String>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.main_llv_item, null)

        val tvListTitle = view.findViewById<TextView>(R.id.tvListTitle)

        val strTitle = mainList[position]

        tvListTitle.text = strTitle

        return view
    }

    override fun getItem(position: Int): Any {
        return mainList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mainList.size
    }
}