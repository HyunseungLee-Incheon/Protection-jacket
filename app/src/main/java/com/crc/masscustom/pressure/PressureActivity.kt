package com.crc.masscustom.pressure

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.crc.masscustom.R
import kotlinx.android.synthetic.main.activity_pressure.*
import java.util.*

class PressureActivity : AppCompatActivity(), View.OnClickListener {

    private val mAlertTimer by lazy { Timer() }
    var isAlertOn = false

    lateinit var iv_alert: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pressure)

        tv_toolbar_title.text = getString(R.string.str_pressure_title)
        bt_toolbar_back.setOnClickListener(this)

        iv_alert = findViewById<ImageView>(R.id.iv_alert)
        iv_alert.setImageResource(R.drawable.haptic_pressure_siren_image1)

        aletByPressure()

    }

    fun aletByPressure() {
        val task = object : TimerTask() {
            override fun run() {

                if(isAlertOn) {
                    iv_alert.setImageResource(R.drawable.haptic_pressure_siren_image1)

                    isAlertOn = false
                } else {
                    iv_alert.setImageResource(R.drawable.haptic_pressure_siren_image2)

                    isAlertOn = true
                }

            }

        }
        mAlertTimer.schedule(task, 0,1000)

    }
    override fun onClick(v: View?) {

    }

}