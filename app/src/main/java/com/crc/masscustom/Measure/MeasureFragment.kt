package com.crc.masscustom.measure

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import java.util.*
import kotlin.concurrent.timer

class MeasureFragment : Fragment() {

    private var time = 0;
    private var timerTask: Timer? = null;
    private var timerMeasureTask: Timer? = null;

    private var tvLoading : TextView? = null;
    var activityCallback: MeasureFragment.progressListener? = null
    private var bIsFinish: Boolean = false

    interface progressListener {
        fun onFinishMeasure()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_measure, container, false)
//
//        val ivLoading = view.findViewById<ImageView>(R.id.ivLoading)
//
//        ivLoading.setOnClickListener {
//            Log.e("eleutheria", "imageview click!!")
//
//        }
        tvLoading = view.findViewById<TextView>(R.id.tvLoading)
        tvLoading?.text = "0 %"

        Constants.bIsStartMeasure = false
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun startInitMeasure() {
        timerMeasureTask = timer(period = 1000) {
            time++

            if(!Constants.bIsStartMeasure && !bIsFinish && time >= 6) {
                Constants.bIsStartMeasure = false
                bIsFinish = true
                timerMeasureTask?.cancel()
                finishMeasure()
            }
//            if(!Constants.bIsStartMeasure && !bIsFinish && time >= 20) {
//                Constants.bIsStartMeasure = true
//            }
//            if(Constants.bIsStartMeasure && !bIsFinish && time >= 50) {
//                Constants.bIsStartMeasure = false
//                bIsFinish = true
//                timerMeasureTask?.cancel()
//                finishMeasure()
//            }
////            Log.e("eleutheria", "time : ${time}")
//            runOnUiThread {
//                tvLoading?.text = "${time * 2} ${getString(R.string.str_common_percentage)}"
//            }
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activityCallback = context as progressListener

        timerTask = timer(period = 3000) {
            startInitMeasure()
            timerTask?.cancel()
        }

    }

    override fun onPause() {
        super.onPause()

        timerTask?.cancel()
        timerMeasureTask?.cancel()
    }

    override fun onDetach() {
        super.onDetach()

        timerTask?.cancel()
        timerMeasureTask?.cancel()
    }

    private fun finishMeasure() {
//        Log.e("eleutheria", "time task : ${time}")
        activityCallback?.onFinishMeasure()
    }
}