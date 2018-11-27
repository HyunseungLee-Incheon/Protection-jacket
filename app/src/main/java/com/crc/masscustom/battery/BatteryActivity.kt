package com.crc.masscustom.battery

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import kotlinx.android.synthetic.main.activity_battery.*
import java.util.*
import kotlin.concurrent.timer

class BatteryActivity : AppCompatActivity() {

    private var timerTask: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery)

        setTitle(R.string.str_battery_title)

        tvDeviceName.text = Constants.strDeviceName

        tvQuantity.setText("100 %")

        displayPercentage()
    }

    private fun displayPercentage() {
        timerTask = timer(period = Constants.BATTERY_DISPLAY_REFRESH_TIME) {
            runOnUiThread {
                tvQuantity.setText("${Constants.batteryPercentage} %")
            }
        }
    }
}