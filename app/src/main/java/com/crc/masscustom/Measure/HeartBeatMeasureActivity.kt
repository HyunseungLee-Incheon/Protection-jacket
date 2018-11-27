package com.crc.masscustom.Measure

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import kotlinx.android.synthetic.main.activity_main.*

class HeartBeatMeasureActivity : AppCompatActivity(), MeasureFragment.progressListener, MeasureResultFragment.fragmentFinishListener {

    val frgHeartBeat = MeasureFragment()
    val frgMeasureResult = MeasureResultFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heartbeatmeasure)

        setTitle(R.string.str_measure_title)
        tvDeviceName.text = Constants.strDeviceName

        changeFragment(frgHeartBeat)

//        Handler().postDelayed({
//            changeFragment(frgMeasureResult)
//        }, 3000)
    }

    private fun changeFragment(frag : Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.flFragContainer, frag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onFinishMeasure() {

//        Log.e("eleutheria", "finish and change Fragment")
        changeFragment(frgMeasureResult)
    }
    override fun onFinishResult() {
        finish()
    }
}