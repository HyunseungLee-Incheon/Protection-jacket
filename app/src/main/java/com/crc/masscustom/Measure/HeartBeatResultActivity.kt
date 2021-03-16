package com.crc.masscustom.measure

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import com.crc.masscustom.base.CurrentDate
import com.crc.masscustom.base.MeasuredData
import com.crc.masscustom.database.DBHeartBeatModel
import com.crc.masscustom.main.LoadingActivity
import com.crc.masscustom.main.MainGridActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main_grid.tv_toolbar_title
import kotlinx.android.synthetic.main.activity_pressure.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class HeartBeatResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var arHBData : ArrayList<String>

    val realm: Realm = Realm.getDefaultInstance()

    var mResultDate : CurrentDate = CurrentDate()

    lateinit var tvResultDate : TextView
    lateinit var tvResultHB : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_heartbeatresult)

        tv_toolbar_title.text = getString(R.string.str_measure_title)
        bt_toolbar_back.setOnClickListener(this)

        val commonUtils = CommonUtils()

//        val intent = intent
//        if(intent != null) {
//            arHBData = intent.getStringArrayListExtra(Constants.HB_MEASUREMENT_DATA)
//            Log.e("eleutheria", "arHBData : Size $arHBData")
//        }

        tvResultDate = findViewById<TextView>(R.id.tvResultDate)
        tvResultHB = findViewById<TextView>(R.id.tvResultHB)

        val avgHeartBeat = Constants.nAvgHeartBeat

//        val alMeasureHeartBeat = arHBData

//        val avgHeartBeat = sumHeartBeat(alMeasureHeartBeat).toInt()
        //avgHeartBeat = 76
        setResultHB(avgHeartBeat)

        val curDate = commonUtils.getCurrentDate()
        setCurDate(curDate)
        makeResultDate(curDate)

        val btSaveResult = findViewById<Button>(R.id.btSaveResult)
        btSaveResult.setOnClickListener {
            //            Log.e("eleutheria", "Save Button Click!!")

            realm.beginTransaction()

            var newId: Long = 1
            if(realm.where(DBHeartBeatModel::class.java).max("id") != null) {
                newId = realm.where(DBHeartBeatModel::class.java).max("id") as Long + 1
            }
            val storedData: MeasuredData = commonUtils.storeMeasuredData(mResultDate, avgHeartBeat, Constants.USER_STATUS_NORMAL)
            val insertData = realm.createObject(DBHeartBeatModel::class.java, newId)
            insertData.year = storedData.nYear
            insertData.month = storedData.nMonth
            insertData.day = storedData.nDay
            insertData.hour = storedData.nHour
            insertData.minute = storedData.nMinute
            insertData.second = storedData.nSecond
            insertData.heartbeat = storedData.nHeartbeat
            insertData.status = storedData.nStatus

            realm.commitTransaction()

            finishResult()
        }

        LoadingActivity.mBluetoothLeService!!.writeCharacteristic(avgHeartBeat.toString())
    }

//    private fun sumHeartBeat(measuredHeartBeat: ArrayList<String>): Long {
//
//        var sumHeartBeat : Long = 0
//
//        for(heartBeat in measuredHeartBeat) {
//            sumHeartBeat += heartBeat.toInt()
//        }
//
//        var avgHeartBeat : Long = 0
//        if(sumHeartBeat > 0) {
//            avgHeartBeat = sumHeartBeat / measuredHeartBeat.size
//        }
//
//        return avgHeartBeat
//    }

    private fun setCurDate(curDate : List<String>) {
        var curYYMMDD = "${curDate[0]}/${curDate[1]}/${curDate[2]}"

        tvResultDate.text = curYYMMDD
    }

    private fun setResultHB(resultHB: Int) {
        tvResultHB.text = resultHB.toString()
    }

    private fun makeResultDate(curDate : List<String>) {
        mResultDate.nYear = curDate[0].toInt()
        mResultDate.nMonth = curDate[1].toInt()
        mResultDate.nDay = curDate[2].toInt()
        mResultDate.nHour = curDate[3].toInt()
        mResultDate.nMinute = curDate[4].toInt()
        mResultDate.nSecond = curDate[5].toInt()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(intentFor<MainGridActivity>().clearTask().newTask())
    }

    private fun finishResult() {
        startActivity(intentFor<MainGridActivity>().clearTask().newTask())
    }
}