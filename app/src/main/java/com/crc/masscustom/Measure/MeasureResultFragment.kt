package com.crc.masscustom.measure

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import com.crc.masscustom.base.CurrentDate
import com.crc.masscustom.base.MeasuredData
import com.crc.masscustom.database.dbHeartBeatManager
import com.crc.masscustom.database.dbHeartBeatModel
import io.realm.Realm

class MeasureResultFragment : Fragment() {


    lateinit var heartBeatManager: dbHeartBeatManager

    val realm: Realm = Realm.getDefaultInstance()

    var mResultDate : CurrentDate = CurrentDate()

    lateinit var tvResultDate : TextView
    lateinit var tvResultHB : TextView

    var activityCallback: fragmentFinishListener? = null


    interface fragmentFinishListener {
        fun onFinishResult()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_measureresult, container, false)

        val commonUtils = CommonUtils()

        tvResultDate = view.findViewById<TextView>(R.id.tvResultDate)
        tvResultHB = view.findViewById<TextView>(R.id.tvResultHB)

        val alMeasureHeartBeat = Constants.measureingHeartBeat

        val avgHeartBeat = sumHeartBeat(alMeasureHeartBeat).toInt()
        setResultHB(avgHeartBeat)

        var curDate = commonUtils.getCurrentDate()
        setCurDate(curDate)
        makeResultDate(curDate)

        val btSaveResult = view.findViewById<Button>(R.id.btSaveResult)
        btSaveResult.setOnClickListener {
//            Log.e("eleutheria", "Save Button Click!!")

            realm.beginTransaction()

            var newId: Long = 1
            if(realm.where(dbHeartBeatModel::class.java).max("id") != null) {
                newId = realm.where(dbHeartBeatModel::class.java).max("id") as Long + 1
            }
            val storedData: MeasuredData = commonUtils.storeMeasuredData(mResultDate, avgHeartBeat, Constants.USER_STATUS_NORMAL)
            val insertData = realm.createObject(dbHeartBeatModel::class.java, newId)
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

        return view
    }

    private fun sumHeartBeat(measuredHeartBeat: ArrayList<Int>): Long {

        var sumHeartBeat : Long = 0

        for(heartBeat in measuredHeartBeat) {
            sumHeartBeat += heartBeat
        }

        var avgHeartBeat : Long = 0
        if(sumHeartBeat > 0) {
            avgHeartBeat = sumHeartBeat / measuredHeartBeat.size
        }

        return avgHeartBeat
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

    private fun finishResult() {
        activityCallback?.onFinishResult()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        activityCallback = context as fragmentFinishListener
    }
}