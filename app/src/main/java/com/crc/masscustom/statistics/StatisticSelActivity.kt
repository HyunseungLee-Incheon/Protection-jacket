package com.crc.masscustom.statistics

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.crc.masscustom.R
import kotlinx.android.synthetic.main.activity_statistic_sel.*
import org.jetbrains.anko.startActivity

class StatisticSelActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_statistic_sel)

        tv_toolbar_title.text = getString(R.string.str_statistic_title)
        bt_toolbar_back.setOnClickListener(this)

        bt_statistic_hb.setOnClickListener(this)
        bt_statistic_temp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_statistic_hb -> {
                startActivity<StatisticActivity>()
            }
            R.id.bt_statistic_temp -> {
                startActivity<StatisticTemperatureActivity>()
            }
        }
    }

}