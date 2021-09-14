package com.crc.masscustom.statistics

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.crc.masscustom.R
import com.crc.masscustom.main.MainGridActivity

class StatisticSelActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btStatisticHb : Button
    private lateinit var btStatisticTemp : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_statistic_sel)

        val tvToolbarTitle : TextView = findViewById(R.id.tv_toolbar_title)
        tvToolbarTitle.text = getString(R.string.str_statistic_title)

        val btToolbarBack : Button = findViewById(R.id.bt_toolbar_back)
        btToolbarBack.setOnClickListener(this)


        btStatisticHb = findViewById(R.id.bt_statistic_hb)
        btStatisticHb.setOnClickListener(this)
        btStatisticTemp = findViewById(R.id.bt_statistic_temp)
        btStatisticTemp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_statistic_hb -> {
                val intent = Intent(this, StatisticActivity::class.java)
                startActivity(intent)
            }
            R.id.bt_statistic_temp -> {
                val intent = Intent(this, StatisticTemperatureActivity::class.java)
                startActivity(intent)
            }
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainGridActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}