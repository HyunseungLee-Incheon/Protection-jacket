package com.crc.masscustom.finedust

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity

class FineDustActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var tvFineDustText : TextView
    lateinit var ivFineDustBg : ImageView
    lateinit var ivUm : ImageView
    lateinit var ivFineDustGraph : ImageView
    var nFineDust = 0
    var strReceiveData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_finedust)

        var tvToolbarTitle : TextView = findViewById(R.id.tv_toolbar_title)
        tvToolbarTitle.text = getString(R.string.str_finedust_title)

        var btToolbarBack : Button = findViewById(R.id.bt_toolbar_back)
        btToolbarBack.setOnClickListener(this)

        tvFineDustText = findViewById(R.id.tv_finedust_text)
        ivFineDustBg = findViewById(R.id.iv_finedust_bg)
        ivUm = findViewById(R.id.iv_finedust_um_bg)
        ivFineDustGraph = findViewById(R.id.iv_finedust_graph)

        tvFineDustText.text = nFineDust.toString()
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver, IntentFilter(
                Constants.MESSAGE_SEND_FINEDUST
            )
        )
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_toolbar_back -> {
                onBackPressed()
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
                val message = intent.getStringExtra("value")

                tvFineDustText.text = message
            }
        }

    }
}