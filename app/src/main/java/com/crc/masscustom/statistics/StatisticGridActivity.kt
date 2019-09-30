package com.crc.masscustom.statistics

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.crc.masscustom.R
import kotlinx.android.synthetic.main.activity_statistic.*
import kotlinx.android.synthetic.main.activity_statistics_grid.*
import org.jetbrains.anko.startActivity

class StatisticGridActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
    View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_grid)

        tv_toolbar_title.text = getString(R.string.str_temperature_title)
        bt_toolbar_back.setOnClickListener(this)

        val titleList = arrayListOf<String>(
            getString(R.string.str_measure_title),
            getString(R.string.str_temperature_title)
        )

        val listAdapter = StatisticGridAdapter(this, titleList)
        gvStatisticsList.adapter = listAdapter
        gvStatisticsList.setOnItemClickListener(this)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position) {
            0 -> { // heartbeat
                startActivity<StatisticActivity>()
            }
            1 -> { // pressure
                startActivity<StatisticTemperatureActivity>()
            }
        }
    }

    override fun onClick(v: View?) {

    }
}