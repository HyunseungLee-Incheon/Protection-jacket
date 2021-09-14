package com.crc.masscustom.gyro

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity
import java.util.*

class GyroActivity : AppCompatActivity(), View.OnClickListener {

    val handler: Handler = Handler1()
    private val mAlertTimer by lazy { Timer() }
    var isAlertOn = false

    lateinit var ivGyroReady : ImageView
    lateinit var ivGyroOnoff : ImageView
    lateinit var clGyroLayout: ConstraintLayout
    var bWarning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gyro)

        var tvToolbarTitle : TextView = findViewById(R.id.tv_toolbar_title)
        tvToolbarTitle.text = getString(R.string.str_gyro_title)

        var btToolbarBack : Button = findViewById(R.id.bt_toolbar_back)
        btToolbarBack.setOnClickListener(this)

        ivGyroReady = findViewById(R.id.iv_gyro_ready)
        ivGyroReady.setOnClickListener(this)
        ivGyroOnoff = findViewById(R.id.iv_gyro_onoff)
        clGyroLayout = findViewById(R.id.cl_gyro_layout)

//        alertByGyro()
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver, IntentFilter(
                Constants.MESSAGE_SEND_GYRO
            )
        )
    }

    private fun alertByGyro() {
        val task = object : TimerTask() {
            override fun run() {

                runOnUiThread {
                    ivGyroReady.visibility = View.GONE
                    ivGyroOnoff.visibility = View.VISIBLE
                }

                val msg = handler.obtainMessage()
                handler.sendMessage(msg)
//                if(isAlertOn) {
//                    ivGyroOnoff.setImageResource(R.drawable.gyrosensing_emergency_image2)
//                    clGyroLayout.backgroundColor = resources.getColor(R.color.colorWhite)
//
//                    isAlertOn = false
//                } else {
//                    ivGyroOnoff.setImageResource(R.drawable.gyrosensing_ready_image)
////                    clGyroLayout.backgroundColor = resources.getColor(R.color.colorE01F56)
//                    clGyroLayout.backgroundColor = resources.getColor(R.color.colorActionBar)
//
//                    isAlertOn = true
//                }

            }

        }
        mAlertTimer.schedule(task, 1000, 1000)
    }

    inner class Handler1 : Handler() {
        override fun handleMessage(msg: Message) = if(isAlertOn) {
            ivGyroOnoff.setImageResource(R.drawable.gyrosensing_emergency_image2)
            clGyroLayout.setBackgroundColor(resources.getColor(R.color.colorWhite))

            isAlertOn = false
        } else {
            ivGyroOnoff.setImageResource(R.drawable.gyrosensing_emergency_image1)
            clGyroLayout.setBackgroundColor(resources.getColor(R.color.colorActionBar))

            isAlertOn = true
        }
    }

    private fun sendSMS() {
        val strPhoneNumber = Constants.strGyroNumber

        val smsManager = SmsManager.getDefault()

//        val message = "Gyro Action Message!! "
        val message = "비상 상황입니다! 구조가 필요합니다!"
        val strFirstString = strPhoneNumber.substring(0, 1)

        if(strFirstString.equals("0")) {
            Log.e("eleutheria", "strPhoneNumber : $strPhoneNumber")
            smsManager.sendTextMessage(strPhoneNumber, null, message, null, null)
        }
    }

    private fun sendCall() {
        val strPhoneNumber = Constants.strGyroNumber

        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$strPhoneNumber"))
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
            R.id.iv_gyro_ready -> {
                alertByGyro()
                sendSMS()
                sendCall()
            }
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()

        val intent = Intent(this, MainGridActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.hasExtra("value")) {
                val message = intent!!.getStringExtra("value")
                if(message.equals("1")) {
                    if(!bWarning) {
                        Log.e("eleutheria", "warning : $bWarning")
                        bWarning = true
                        alertByGyro()
                        sendSMS()
                        sendCall()
                    }
                }
            }
        }

    }

}