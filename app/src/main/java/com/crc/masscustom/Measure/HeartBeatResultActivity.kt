package com.crc.masscustom.measure

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import com.crc.masscustom.base.CurrentDate
import com.crc.masscustom.base.MeasuredData
import com.crc.masscustom.database.DBHeartBeatModel
import com.crc.masscustom.main.LoadingActivity
import com.crc.masscustom.main.MainGridActivity
import io.realm.Realm

class HeartBeatResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var arHBData : ArrayList<String>

    val realm: Realm = Realm.getDefaultInstance()

    var mResultDate : CurrentDate = CurrentDate()

    lateinit var tvResultDate : TextView
    lateinit var tvResultHB : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_heartbeatresult)

        var tvToolbarTitle : TextView = findViewById(R.id.tv_toolbar_title)
        tvToolbarTitle.text = getString(R.string.str_measure_title)

        var btToolbarBack : Button = findViewById(R.id.bt_toolbar_back)
        btToolbarBack.setOnClickListener(this)

        val commonUtils = CommonUtils()

        tvResultDate = findViewById<TextView>(R.id.tvResultDate)
        tvResultHB = findViewById<TextView>(R.id.tvResultHB)

        val avgHeartBeat = Constants.nAvgHeartBeat

//        val avgHeartBeat = sumHeartBeat(alMeasureHeartBeat).toInt()
//        avgHeartBeat = 76

        setResultHB(avgHeartBeat)

        val curDate = commonUtils.getCurrentDate()
        setCurDate(curDate)
        makeResultDate(curDate)

        val btSaveResult = findViewById<Button>(R.id.btSaveResult)
        btSaveResult.setOnClickListener {

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
        val intent = Intent(this, MainGridActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun finishResult() {
        val intent = Intent(this, MainGridActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}