package com.crc.masscustom.statistics

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import kotlinx.android.synthetic.main.activity_statistic.*

class StatisticActivity : AppCompatActivity(), View.OnClickListener {

    var frgStatisticDay = StasticDayFragment()
    var frgStatisticMonth = StatisticMonthFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        setTitle(R.string.str_statistic_title)

        tvDeviceName.text = Constants.strDeviceName

        btDay.setOnClickListener(this)
        btMonth.setOnClickListener(this)

        setCurrentButtonState()

        changeFragment(frgStatisticDay)
    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.btDay -> {
                changeFragment(frgStatisticDay)
            }
            R.id.btMonth -> {
                changeFragment(frgStatisticMonth)
            }

        }

    }

    private fun changeFragment(frag : Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.flFragContainer, frag)
        transaction.addToBackStack(null)
        transaction.commit()

        if(frag == frgStatisticDay) {
            Constants.STASTIC_CURRENT_STATE = "DAY"
        } else {
            Constants.STASTIC_CURRENT_STATE = "MONTH"
        }
        setCurrentButtonState()
    }

    private fun setCurrentButtonState() {
        if(Constants.STASTIC_CURRENT_STATE == "DAY") {
            btDay.setBackgroundResource(R.drawable.img_bt_sel)
            btMonth.setBackgroundResource(R.drawable.img_bt_nor)
        } else {
            btDay.setBackgroundResource(R.drawable.img_bt_nor)
            btMonth.setBackgroundResource(R.drawable.img_bt_sel)
        }
    }
}