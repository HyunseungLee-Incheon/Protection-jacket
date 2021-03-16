package com.crc.masscustom.main

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

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
        startActivity(intentFor<LoadingActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_HB_RESULT).clearTask().newTask())
    }
}
