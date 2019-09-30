package com.crc.masscustom.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.crc.masscustom.measure.HeartBeatMeasureActivity
import com.crc.masscustom.R
import com.crc.masscustom.gyro.GyroActivity
import com.crc.masscustom.pressure.PressureActivity
import com.crc.masscustom.rear.RearActivity
import com.crc.masscustom.setting.SettingActivity
import com.crc.masscustom.statistics.StatisticGridActivity
import com.crc.masscustom.statistics.StatisticSelActivity
import com.crc.masscustom.temperature.TemperatureActivity
import com.crc.masscustom.uv.UvActivity
import kotlinx.android.synthetic.main.activity_main_grid.*
import org.jetbrains.anko.startActivity


class MainGridActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_grid)

        tv_toolbar_title.text = getString(R.string.str_main_title)

        val mainIconList = arrayListOf<Int>(
            R.drawable.main_icon_heartbeat,
            R.drawable.main_icon_haptic_pressure,
            R.drawable.main_icon_rear,
            R.drawable.main_icon_uv,
            R.drawable.main_icon_gyro,
            R.drawable.main_icon_temperature,
            R.drawable.main_icon_statistics,
            R.drawable.main_icon_set_up
        )

        val listAdapter = MainGridAdapter(this, mainIconList)
        gvMainList.adapter = listAdapter
        gvMainList.setOnItemClickListener(this)

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.e("eleutheria", "position : " + position)
        when(position) {
            0 -> { // heartbeat
                startActivity<HeartBeatMeasureActivity>()
            }
            1 -> { // pressure
                startActivity<PressureActivity>()
            }
            2 -> { // rear
                startActivity<RearActivity>()
            }
            3 -> { // uv
                startActivity<UvActivity>()
            }
            4 -> { // gyro
                startActivity<GyroActivity>()
            }
            5 -> { // temperature
                startActivity<TemperatureActivity>()
            }
            6 -> { // statistic
                startActivity<StatisticSelActivity>()
            }
            7 -> { // setting
                startActivity<SettingActivity>()
            }
        }

    }

}