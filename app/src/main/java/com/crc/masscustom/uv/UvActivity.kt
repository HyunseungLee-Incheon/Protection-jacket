package com.crc.masscustom.uv

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import kotlinx.android.synthetic.main.activity_uv.*
import org.jetbrains.anko.backgroundColor


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

        var nUvFactor = 9
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

    }

}