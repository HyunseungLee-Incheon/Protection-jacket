package com.crc.masscustom.base

import android.content.res.Resources
import android.util.Log
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.crc.masscustom.database.dbHeartBeatModel
import io.realm.RealmResults
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


public class CommonUtils {

    public fun printScreenInfo() {
        Log.d("eleutheria", Resources.getSystem().displayMetrics.density.toString())

        Log.d("eleutheria", Resources.getSystem().displayMetrics.densityDpi.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.scaledDensity.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.heightPixels.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.widthPixels.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.xdpi.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.ydpi.toString())

    }

    public fun storeMeasuredData(resultDate: CurrentDate, nHeartBeat: Int, nStatus: Int) : MeasuredData {

        var storeData : MeasuredData = MeasuredData()
        storeData.nYear = resultDate.nYear
        storeData.nMonth = resultDate.nMonth
        storeData.nDay = resultDate.nDay

        storeData.nHour = resultDate.nHour
        storeData.nMinute = resultDate.nMinute
        storeData.nSecond = resultDate.nSecond

        storeData.nHeartbeat = nHeartBeat
        storeData.nStatus = nStatus

        Constants.alMeasuredData = storeData

        return Constants.alMeasuredData!!
    }

    public fun makeTestData(nYear: Int, nMonth: Int, nDay: Int, nHour: Int, nMinute: Int, nSecond: Int, nHearBeat: Int, nStatus: Int): MeasuredData {
        var storeData : MeasuredData = MeasuredData()

        storeData.nYear = nYear
        storeData.nMonth = nMonth
        storeData.nDay = nDay

        storeData.nHour = nHour
        storeData.nMinute = nMinute
        storeData.nSecond = nSecond

        storeData.nHeartbeat = nHearBeat
        storeData.nStatus = nStatus

        return storeData
    }

    public fun getCurrentDate() : List<String> {

        val sdf = SimpleDateFormat("yyyy/MM/dd/hh/mm/ss")
        val currentDate = sdf.format(Date())

        var curDate = currentDate.split("/")


        return curDate
    }

    public fun calcAverage(bpmDatas : RealmResults<dbHeartBeatModel>) : Int {
        var nAverage = 0
        var nTotalBPM : Long = 0
        var nTotalSize : Int = bpmDatas.size

        if(bpmDatas.size < 1) {
            nAverage = 0
        } else {
            for(bpmData in bpmDatas) {
                nTotalBPM += bpmData.heartbeat
            }
            nAverage = (nTotalBPM / nTotalSize).toInt()
        }

        return nAverage
    }

    public fun calcMonthlyAverage(datas : ArrayList<MonthlyStatisticData>) : Int {
        var result : Int = 0

        if(datas.size < 1) {
            return result
        }
        for(data in datas) {
            result += data.nBPM
        }
        if(result > 0) {
            result /= datas.size
        }

        return result
    }

    public fun calcMinAverage(datas : ArrayList<MonthlyStatisticData>) : Int {
        var result : Int = 0

        if(datas.size < 1) {
            return result
        }
        for(data in datas) {
            result += data.nMin
        }
        if(result > 0) {
            result /= datas.size
        }

        return result
    }

    public fun calcMaxAverage(datas : ArrayList<MonthlyStatisticData>) : Int {
        var result : Int = 0

        if(datas.size < 1) {
            return result
        }
        for(data in datas) {
            result += data.nMax
        }
        if(result > 0) {
            result /= datas.size
        }

        return result
    }

}