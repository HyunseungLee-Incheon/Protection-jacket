package com.crc.masscustom.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import com.crc.masscustom.Main.MainActivity
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import com.crc.masscustom.bluetooth.BluetoothActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.concurrent.timer

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        var rotate = AnimationUtils.loadAnimation(this, R.anim.rotation)
//        rotate.fillAfter = true
//        ivLoading.startAnimation(rotate)

//        ivLoading.animate().rotation(360F).duration = 2000
//        ivLoading.animate().start()
    }

    override fun onStart() {
        super.onStart()

        var commonUtils = CommonUtils()
        var curDate = commonUtils.getCurrentDate()

        Constants.curYearOfDay = curDate[0].toInt()
        Constants.curMonthOfDay = curDate[1].toInt()
        Constants.curDayOfDay = curDate[2].toInt()
        Constants.curYearOfMonth = curDate[0].toInt()
        Constants.curMonthOfMonth = curDate[1].toInt()

        Handler().postDelayed({
            endLoading()
        }, Constants.SPLASH_LOADING_TIME)

    }

    private fun endLoading() {
        tvLoading.setText(R.string.str_splash_welcome)

        Handler().postDelayed({
            startToActivity<Any>()
        }, Constants.SPLASH_WAITING_TIME)
    }

    private fun <T> startToActivity() {
        finish()
        startActivity<BluetoothActivity>()
//        startActivity<MainActivity>()
    }

}