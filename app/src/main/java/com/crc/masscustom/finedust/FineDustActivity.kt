package com.crc.masscustom.finedust

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity
import kotlinx.android.synthetic.main.activity_temperature.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

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

        tv_toolbar_title.text = getString(R.string.str_finedust_title)
        bt_toolbar_back.setOnClickListener(this)

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
        startActivity(intentFor<MainGridActivity>().newTask().clearTop())
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