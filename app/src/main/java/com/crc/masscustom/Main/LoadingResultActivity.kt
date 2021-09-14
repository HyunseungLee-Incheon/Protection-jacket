package com.crc.masscustom.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants

class LoadingResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_loading_result)

        Handler().postDelayed({
            startToActivity<Any>()
        }, Constants.SPLASH_LOADING_TIME)
    }

    private fun <T> startToActivity() {
        Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_HB_RESULT

        val intent = Intent(this, LoadingActivity::class.java)
        intent.putExtra(Constants.HB_MEASUREMENT_DATA, Constants.MAIN_FUNCTION_INDEX_HB_RESULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
