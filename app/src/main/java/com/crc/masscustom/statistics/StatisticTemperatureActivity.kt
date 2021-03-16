package com.crc.masscustom.statistics

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity
import kotlinx.android.synthetic.main.activity_statistic.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class StatisticTemperatureActivity : AppCompatActivity(), View.OnClickListener {

    var frgStatisticDay = StatisticTemperatureDayFragment()
    var frgStatisticMonth = StatisticTemperatureMonthFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_statistic)

        tv_toolbar_title.text = getString(R.string.str_statistic_Temperature_title)
        bt_toolbar_back.setOnClickListener(this)
//        setTitle(R.string.str_statistic_Temperature_title)

        bt_statistic_day.setOnClickListener(this)
        bt_statistic_month.setOnClickListener(this)

        setCurrentButtonState()

        changeFragment(frgStatisticDay)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_statistic_day -> {
                changeFragment(frgStatisticDay)
            }
            R.id.bt_statistic_month -> {
                changeFragment(frgStatisticMonth)
            }
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(intentFor<StatisticSelActivity>().clearTask().newTask())
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
            bt_statistic_day.setBackgroundResource(R.drawable.statistics_daily_daily_button_normal)
            bt_statistic_month.setBackgroundResource(R.drawable.statistics_daily_monthly_button_normal)
        } else {
            bt_statistic_day.setBackgroundResource(R.drawable.statistics_monthly_daily_button_normal)
            bt_statistic_month.setBackgroundResource(R.drawable.statistics_monthly_monthly_button_normal)
        }
    }

}