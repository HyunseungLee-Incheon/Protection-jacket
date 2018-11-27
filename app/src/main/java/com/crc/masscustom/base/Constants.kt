package com.crc.masscustom.base

class Constants {
    companion object {

        var strDeviceName = "Not Connected"
        val SPLASH_LOADING_TIME : Long = 3000
        val SPLASH_WAITING_TIME : Long = 1000

        val BATTERY_DISPLAY_REFRESH_TIME : Long = 1000

        val MEASURE_WAITING_TIME : Long = 20000
        val MEASURE_MEASURING_TIME : Long = 30000
        var bIsStartMeasure : Boolean = false

        var measureingHeartBeat = ArrayList<Int>()
        var batteryPercentage : String = "100"

        val USER_STATUS_NORMAL = 0

        val DB_NAME_HEARTBEAT = "heartbeat.realm"

        var STASTIC_CURRENT_STATE = "DAY"

        var alMeasuredData: MeasuredData? = null

        val REQUEST_ENABLE_BT = 1
        // Stops scanning after 10 seconds.
        val SCAN_PERIOD: Long = 10000
        val PERMISSION_REQUEST_COARSE_LOCATION = 1


        var curYearOfDay = 2018
        var curMonthOfDay = 11
        var curDayOfDay = 16
        var curYearOfMonth = 2018
        var curMonthOfMonth = 11
    }
}