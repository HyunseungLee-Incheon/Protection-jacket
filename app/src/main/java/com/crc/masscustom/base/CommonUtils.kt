package com.crc.masscustom.base

import android.content.res.Resources
import android.util.Log
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.crc.masscustom.database.dbHeartBeatModel
import io.realm.RealmResults
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CommonUtils {

    fun printScreenInfo() {
        Log.d("eleutheria", Resources.getSystem().displayMetrics.density.toString())

        Log.d("eleutheria", Resources.getSystem().displayMetrics.densityDpi.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.scaledDensity.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.heightPixels.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.widthPixels.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.xdpi.toString())
        Log.d("eleutheria", Resources.getSystem().displayMetrics.ydpi.toString())

    }

    fun storeMeasuredData(resultDate: CurrentDate, nHeartBeat: Int, nStatus: Int) : MeasuredData {

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

    fun makeTestData(nYear: Int, nMonth: Int, nDay: Int, nHour: Int, nMinute: Int, nSecond: Int, nHearBeat: Int, nStatus: Int): MeasuredData {
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

    fun getCurrentDate() : List<String> {

        val sdf = SimpleDateFormat("yyyy/MM/dd/hh/mm/ss")
        val currentDate = sdf.format(Date())

        var curDate = currentDate.split("/")


        return curDate
    }

    fun calcAverage(bpmDatas : RealmResults<dbHeartBeatModel>) : Int {
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

    fun calcMonthlyAverage(datas : ArrayList<MonthlyStatisticData>) : Int {
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

    fun calcMinAverage(datas : ArrayList<MonthlyStatisticData>) : Int {
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

    fun calcMaxAverage(datas : ArrayList<MonthlyStatisticData>) : Int {
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

    fun calcRearDetect(distance : Float) : Int {

        var nResult = Constants.REAR_INDEX_ZERO

        if(distance < Constants.REAR_DISTANCE_ZERO) {
            nResult = Constants.REAR_INDEX_ZERO
        } else if(distance < Constants.REAR_DISTANCE_ONE) {
            nResult = Constants.REAR_INDEX_ONE
        } else if(distance < Constants.REAR_DISTANCE_TWO) {
            nResult = Constants.REAR_INDEX_TWO
        } else if(distance < Constants.REAR_DISTANCE_THREE) {
            nResult = Constants.REAR_INDEX_THREE
        } else {
            nResult = Constants.REAR_INDEX_FOUR
        }
        return nResult
    }

}