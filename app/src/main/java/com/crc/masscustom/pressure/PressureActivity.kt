package com.crc.masscustom.pressure

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity
import kotlinx.android.synthetic.main.activity_pressure.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.util.*
import android.net.Uri

class PressureActivity : AppCompatActivity(), View.OnClickListener {

    private val mAlertTimer by lazy { Timer() }
    var isAlertOn = false

    lateinit var iv_alert: ImageView
    var strReceiveData : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pressure)

        tv_toolbar_title.text = getString(R.string.str_pressure_title)
        bt_toolbar_back.setOnClickListener(this)

        iv_alert = findViewById<ImageView>(R.id.iv_alert)
        iv_alert.setOnClickListener(this)
        iv_alert.setImageResource(R.drawable.haptic_pressure_siren_image1)
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

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter(
            Constants.MESSAGE_SEND_PRESSURE)
        )
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
            R.id.iv_alert -> {
                aletByPressure()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(intentFor<MainGridActivity>().clearTask().newTask())
    }


    private fun sendSMS() {
        val strPhoneNumber = Constants.strHapticNumber

        val smsManager = SmsManager.getDefault()

        val message = "Gyro Action Message!! "
        val strFirstString = strPhoneNumber.substring(0, 1)

        if(strFirstString.equals("0")) {
            Log.e("eleutheria", "strPhoneNumber : $strPhoneNumber")
            smsManager.sendTextMessage(strPhoneNumber, null, message, null, null)
        }
    }

    private fun sendCall() {
        var strPhoneNumber = Constants.strHapticNumber

        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$strPhoneNumber"))
        startActivity(intent)
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.hasExtra("value")) {
                val message = intent!!.getStringExtra("value")
//                Log.e("eleutheria", "message : $message")

                if(message == ".") {
//                    Log.e("eleutheria", "strReceiveData : $strReceiveData")
                    var arData = strReceiveData.split("\r\n")

                    for(data in arData) {
                        if(data.contains("P")) {
                            Log.e("eleutheria", "data : $data")

                            if(data == "P1" || data == ".P1") {
                                Log.e("eleutheria", "pressure alert")
                                aletByPressure()
//                                Log.e("eleutheria", "strPhoneNumber : ${Constants.strHapticNumber}")
//                                sendSMS()
//                                sendCall()
                            }
                        }
                    }
                    strReceiveData = ""
                } else {
                    strReceiveData += message
                }
            }
        }
    }

}