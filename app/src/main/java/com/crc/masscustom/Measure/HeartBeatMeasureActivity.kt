package com.crc.masscustom.measure

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.crc.masscustom.R
import kotlinx.android.synthetic.main.activity_main_grid.*
import kotlinx.android.synthetic.main.activity_main_grid.tv_toolbar_title
import kotlinx.android.synthetic.main.activity_pressure.*

class HeartBeatMeasureActivity : AppCompatActivity(), MeasureFragment.progressListener, MeasureResultFragment.fragmentFinishListener,
    View.OnClickListener {

    val frgHeartBeat = MeasureFragment()
    val frgMeasureResult = MeasureResultFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heartbeatmeasure)

        tv_toolbar_title.text = getString(R.string.str_measure_title)
        bt_toolbar_back.setOnClickListener(this)

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

    override fun onClick(v: View?) {

    }

}