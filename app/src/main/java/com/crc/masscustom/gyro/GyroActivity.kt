package com.crc.masscustom.gyro

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.crc.masscustom.R
import kotlinx.android.synthetic.main.activity_gyro.*
import org.jetbrains.anko.backgroundColor
import java.util.*

class GyroActivity : AppCompatActivity(), View.OnClickListener {

    private val mAlertTimer by lazy { Timer() }
    var isAlertOn = false

    lateinit var ivGyroReady : ImageView
    lateinit var ivGyroOnoff : ImageView
    lateinit var clGyroLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gyro)

        tv_toolbar_title.text = getString(R.string.str_gyro_title)
        bt_toolbar_back.setOnClickListener(this)

        ivGyroReady = findViewById(R.id.iv_gyro_ready)
        ivGyroOnoff = findViewById(R.id.iv_gyro_onoff)
        clGyroLayout = findViewById(R.id.cl_gyro_layout)

        alertByGyro()
    }

    private fun alertByGyro() {
        val task = object : TimerTask() {
            override fun run() {

                runOnUiThread {
                    ivGyroReady.visibility = View.GONE
                    ivGyroOnoff.visibility = View.VISIBLE
                }


                if(isAlertOn) {
                    ivGyroOnoff.setImageResource(R.drawable.gyrosensing_emergency_image2)
                    clGyroLayout.backgroundColor = resources.getColor(R.color.colorWhite)

                    isAlertOn = false
                } else {
                    ivGyroOnoff.setImageResource(R.drawable.gyrosensing_emergency_image1)
                    clGyroLayout.backgroundColor = resources.getColor(R.color.colorE01F56)

                    isAlertOn = true
                }

            }

        }
        mAlertTimer.schedule(task, 1000,1000)
    }

    override fun onClick(v: View?) {

    }

}