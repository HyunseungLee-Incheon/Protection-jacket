package com.crc.masscustom.uv

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity
import kotlinx.android.synthetic.main.activity_uv.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask


class UvActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var tvUvFactor: TextView
    lateinit var tvUvStatus: TextView
    lateinit var clMainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_uv)

        tv_toolbar_title.text = getString(R.string.str_uv_title)
        bt_toolbar_back.setOnClickListener(this)

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
            clMainLayout.backgroundColor = resources.getColor(R.color.colorUVLow)
        } else if(nUvFactor <= Constants.UV_FACTOR_NORMAL) {
            tvUvFactor.text = nUvFactor.toString()
            tvUvStatus.text = getString(R.string.str_uv_normal)
            clMainLayout.backgroundColor = resources.getColor(R.color.colorUVNormal)

        } else if(nUvFactor <= Constants.UV_FACTOR_HIGH) {
            tvUvFactor.text = nUvFactor.toString()
            tvUvStatus.text = getString(R.string.str_uv_high)
            clMainLayout.backgroundColor = resources.getColor(R.color.colorUVHigh)

        } else if(nUvFactor <= Constants.UV_FACTOR_VERY_HIGH) {
            tvUvFactor.text = nUvFactor.toString()
            tvUvStatus.text = getString(R.string.str_uv_very_high)
            clMainLayout.backgroundColor = resources.getColor(R.color.colorUVVeryHigh)

        } else {
            tvUvFactor.text = nUvFactor.toString()
            tvUvStatus.text = getString(R.string.str_uv_dangerous)
            clMainLayout.backgroundColor = resources.getColor(R.color.colorUVDangerous)

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
        startActivity(intentFor<MainGridActivity>().newTask().clearTop())
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter(Constants.MESSAGE_SEND_UV))
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.hasExtra("value")) {
                val message = intent!!.getStringExtra("value")

                displayUvFactor(message.toInt())
            }
        }

    }

}