package com.crc.masscustom.measure

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity
import kotlinx.android.synthetic.main.activity_main_grid.tv_toolbar_title
import kotlinx.android.synthetic.main.activity_pressure.bt_toolbar_back
import org.jetbrains.anko.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class HeartBeatMeasureActivity : AppCompatActivity(), View.OnClickListener {

    var strReceiveData : String = ""

    private var time = 0;
    private var timerTask: Timer? = null;
    private var timerMeasureTask: Timer? = null;

    private var tvLoading : TextView? = null;
    private var bIsFinish: Boolean = false
    private var arHBData : ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heartbeatmeasure)

        tv_toolbar_title.text = getString(R.string.str_measure_title)
        bt_toolbar_back.setOnClickListener(this)

        tvLoading = findViewById(R.id.tvLoading)
        tvLoading?.text = "0 %"

        Constants.bIsStartMeasure = false

        timerTask = timer(period = 3000) {
            startInitMeasure()
            timerTask?.cancel()
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(intentFor<MainGridActivity>().clearTask().newTask())
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter(
            Constants.MESSAGE_SEND_HB)
        )
    }

    private fun startInitMeasure() {
        timerMeasureTask = timer(period = 1000) {
            time++

            // test
//            if(!Constants.bIsStartMeasure && !bIsFinish && time >= 6) {
//                Constants.bIsStartMeasure = false
//                bIsFinish = true
//                timerMeasureTask?.cancel()
//                finishMeasure()
//            }

            // data measure
            if(!Constants.bIsStartMeasure && !bIsFinish && time >= 20) {
                Constants.bIsStartMeasure = true
            }
            if(Constants.bIsStartMeasure && !bIsFinish && time >= 50) {
                Constants.bIsStartMeasure = false
                bIsFinish = true
                timerMeasureTask?.cancel()
                finishMeasure()
            }
//            Log.e("eleutheria", "time : ${time}")
            runOnUiThread {
                tvLoading?.text = "${time * 2} ${getString(R.string.str_common_percentage)}"
            }
        }

    }

    private fun finishMeasure() {
        startActivity<HeartBeatResultActivity>(Constants.HB_MEASUREMENT_DATA to arHBData)
        Log.e("eleutheria", "finish measure")
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.hasExtra("value")) {
                val message = intent!!.getStringExtra("value")
                Log.e("eleutheria", "message : $message")

                if(message == ".") {
//                    Log.e("eleutheria", "strReceiveData : $strReceiveData")
                    var arData = strReceiveData.split("\r\n")

                    for(data in arData) {
                        if(data.contains("HB")) {
                            if(Constants.bIsStartMeasure && !bIsFinish) {
                                Log.e("eleutheria", "HB : $data")
                                arHBData.add(data.substring(3))
//                                arHBData.add(data)
                            }
                        }
                    }
//                    if(arData.size > 8) {
//                        var fLeftDistance = arData[3].toFloat()
//                        var fBackDistance = arData[5].toFloat()
//                        var fRightDistance = arData[7].toFloat()
//
//                        var nLeftRear = commonUtils.calcRearDetect(fLeftDistance / 1000)
//                        var nBackRear = commonUtils.calcRearDetect(fBackDistance / 1000)
//                        var nRightRear = commonUtils.calcRearDetect(fRightDistance / 1000)
//
//                        displayLeftRear(nLeftRear)
//                        displayRightRear(nRightRear)
//                        displayBackRear(nBackRear)
//                    }
                    strReceiveData = ""
                } else {
                    strReceiveData += message
//                    Log.e("eleutheria", "strReceiveData : $strReceiveData")
                }
            }
        }
    }

}