package com.crc.masscustom.uv

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity


class UvActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var tvUvFactor: TextView
    lateinit var tvUvStatus: TextView
    lateinit var clMainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_uv)

        val tvToolbarTitle : TextView = findViewById(R.id.tv_toolbar_title)
        tvToolbarTitle.text = getString(R.string.str_uv_title)

        val btToolbarBack : Button = findViewById(R.id.bt_toolbar_back)
        btToolbarBack.setOnClickListener(this)

        val commonUtils = CommonUtils()

        tvUvFactor = findViewById<TextView>(R.id.tv_uv_factor)
        tvUvStatus = findViewById<TextView>(R.id.tv_uv_status)
        clMainLayout = findViewById<ConstraintLayout>(R.id.cl_main_layout)

        var nUvFactor = 1
        displayUvFactor(nUvFactor)
    }

    private fun displayUvFactor(nUvFactor: Int) {
        if(nUvFactor <= Constants.UV_FACTOR_LOW) {
            tvUvFactor.text = nUvFactor.toString()
            tvUvStatus.text = getString(R.string.str_uv_low)
            clMainLayout.setBackgroundColor(resources.getColor(R.color.colorUVLow))
        } else if(nUvFactor <= Constants.UV_FACTOR_NORMAL) {
            tvUvFactor.text = nUvFactor.toString()
            tvUvStatus.text = getString(R.string.str_uv_normal)
            clMainLayout.setBackgroundColor(resources.getColor(R.color.colorUVNormal))

        } else if(nUvFactor <= Constants.UV_FACTOR_HIGH) {
            tvUvFactor.text = nUvFactor.toString()
            tvUvStatus.text = getString(R.string.str_uv_high)
            clMainLayout.setBackgroundColor(resources.getColor(R.color.colorUVHigh))

        } else if(nUvFactor <= Constants.UV_FACTOR_VERY_HIGH) {
            tvUvFactor.text = nUvFactor.toString()
            tvUvStatus.text = getString(R.string.str_uv_very_high)
            clMainLayout.setBackgroundColor(resources.getColor(R.color.colorUVVeryHigh))

        } else {
            tvUvFactor.text = nUvFactor.toString()
            tvUvStatus.text = getString(R.string.str_uv_dangerous)
            clMainLayout.setBackgroundColor(resources.getColor(R.color.colorUVDangerous))

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
//        super.onBackPressed()
        val intent = Intent(this, MainGridActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter(Constants.MESSAGE_SEND_UV))
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.hasExtra("value")) {
                val message = intent!!.getStringExtra("value")

                if (message != null) {
                    displayUvFactor(message.toInt())
                }
            }
        }

    }

}