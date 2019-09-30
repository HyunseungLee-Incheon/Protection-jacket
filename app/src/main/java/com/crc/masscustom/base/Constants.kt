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

        val REAR_DISTANCE_ZERO : Float              = 1.5F
        val REAR_DISTANCE_ONE : Float               = 3.0F
        val REAR_DISTANCE_TWO : Float               = 7.0F
        val REAR_DISTANCE_THREE : Float             = 11.0F
        val REAR_DISTANCE_FOUR : Float              = 15.0F

        val REAR_INDEX_ZERO : Int                   = 0
        val REAR_INDEX_ONE : Int                    = 1
        val REAR_INDEX_TWO : Int                    = 2
        val REAR_INDEX_THREE : Int                  = 3
        val REAR_INDEX_FOUR : Int                   = 4

        val UV_FACTOR_LOW : Int                     = 2
        val UV_FACTOR_NORMAL : Int                  = 5
        val UV_FACTOR_HIGH : Int                    = 7
        val UV_FACTOR_VERY_HIGH : Int               = 10
        val UV_FACTOR_DANGEROUS : Int               = 11

        val TEMPERATURE_COLD : Int                  = 16
        val TEMPERATURE_GOOD : Int                  = 28
        val TEMPERATURE_HOT : Int                   = 28

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