package com.crc.masscustom.gyro

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity
import kotlinx.android.synthetic.main.activity_gyro.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.util.*

class GyroActivity : AppCompatActivity(), View.OnClickListener {

    private val mAlertTimer by lazy { Timer() }
    var isAlertOn = false

    lateinit var ivGyroReady : ImageView
    lateinit var ivGyroOnoff : ImageView
    lateinit var clGyroLayout: ConstraintLayout
    var bWarning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gyro)

        tv_toolbar_title.text = getString(R.string.str_gyro_title)
        bt_toolbar_back.setOnClickListener(this)

        ivGyroReady = findViewById(R.id.iv_gyro_ready)
        ivGyroReady.setOnClickListener(this)
        ivGyroOnoff = findViewById(R.id.iv_gyro_onoff)
        clGyroLayout = findViewById(R.id.cl_gyro_layout)

//        alertByGyro()
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter(Constants.MESSAGE_SEND_GYRO))
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

    private fun sendSMS() {
        var strPhoneNumber = Constants.strGyroNumber

        val smsManager = SmsManager.getDefault()

        val message = "Gyro Action Message!! "
        var strFirstString = strPhoneNumber.substring(0, 1)

        if(strFirstString.equals("0")) {
            Log.e("eleutheria", "strPhoneNumber : $strPhoneNumber")
            smsManager.sendTextMessage(strPhoneNumber, null, message, null, null)
        }
    }

    private fun sendCall() {
        var strPhoneNumber = Constants.strGyroNumber

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
//                sendSMS()
//                sendCall()
            }
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        startActivity(intentFor<MainGridActivity>().newTask().clearTop())
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