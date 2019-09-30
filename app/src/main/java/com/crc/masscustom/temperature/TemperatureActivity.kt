package com.crc.masscustom.temperature

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import kotlinx.android.synthetic.main.activity_temperature.*
import org.jetbrains.anko.backgroundColor

class TemperatureActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var tvTemperatureText: TextView
    lateinit var ivTemperatureBg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_temperature)

        tv_toolbar_title.text = getString(R.string.str_temperature_title)
        bt_toolbar_back.setOnClickListener(this)

        tvTemperatureText = findViewById(R.id.tv_temperature_text)
        ivTemperatureBg = findViewById(R.id.iv_temperature_bg)

        var nTemperature = 38
        displayTemperature(nTemperature)
    }

    private fun displayTemperature(nTemperature: Int) {
        if(nTemperature <= Constants.TEMPERATURE_COLD) {
            var strCurTemperature = nTemperature.toString() +  getString(R.string.str_main_default_temperature)
            tvTemperatureText.text = strCurTemperature
            ivTemperatureBg.setImageResource(R.drawable.temperature_cold)
        } else if(nTemperature <= Constants.TEMPERATURE_GOOD) {
            var strCurTemperature = nTemperature.toString() +  getString(R.string.str_main_default_temperature)
            tvTemperatureText.text = strCurTemperature
            ivTemperatureBg.setImageResource(R.drawable.temperature_good)
        } else {
            var strCurTemperature = nTemperature.toString() +  getString(R.string.str_main_default_temperature)
            tvTemperatureText.text = strCurTemperature
            ivTemperatureBg.setImageResource(R.drawable.temperature_hot)
        }
    }

    override fun onClick(v: View?) {

    }
}