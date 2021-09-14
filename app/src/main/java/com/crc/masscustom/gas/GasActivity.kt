package com.crc.masscustom.gas

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity

class GasActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var tvCO2Text : TextView
    lateinit var tvTVOCText : TextView
    lateinit var ivGasBg : ImageView
    lateinit var ivCO2Bg : ImageView
    lateinit var ivTVOCBg : ImageView
    var nCO2 = 0
    var nTVOC = 0
    var strReceiveData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gas)

        var tvToolbarTitle : TextView = findViewById(R.id.tv_toolbar_title)
        tvToolbarTitle.text = getString(R.string.str_gas_title)

        var btToolbarBack : Button = findViewById(R.id.bt_toolbar_back)
        btToolbarBack.setOnClickListener(this)

        tvCO2Text = findViewById(R.id.tv_co2_text)
        tvTVOCText = findViewById(R.id.tv_tvoc_text)
        ivGasBg = findViewById(R.id.iv_gas_bg)
        ivCO2Bg = findViewById(R.id.iv_co2_bg)
        ivTVOCBg = findViewById(R.id.iv_tvoc_bg)

        tvCO2Text.text = nCO2.toString()
        tvTVOCText.text = nTVOC.toString()

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver, IntentFilter(
                Constants.MESSAGE_SEND_GAS
            )
        )
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
                Log.d("eleutheria", String.format("Gas Value : $message"))

                if (message != null) {
                    if(message.contains(Constants.RECIEVE_DATA_PREFIX_CO2)) {
                        val subData = message.substring(2)
                        tvCO2Text.text = subData
                    } else if(message.contains(Constants.RECIEVE_DATA_PREFIX_TVOC)){
                        val subData = message.substring(4)
                        tvTVOCText.text = subData
                    }
                }
            }
        }

    }
}