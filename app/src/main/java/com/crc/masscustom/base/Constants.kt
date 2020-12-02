package com.crc.masscustom.base

class Constants {
    companion object {

        var strDeviceName = "Not Connected"
        var strDeviceAddress = "Not Connected"
        var strHapticNumber : String = "112"
        var strGyroNumber : String = "119"
        val SPLASH_LOADING_TIME : Long = 3000
        val SPLASH_WAITING_TIME : Long = 1000

        val SHARED_PREF_SEUPDATA                        = "setupdData"

        val PREF_HAPTIC_CALL_NUMBER : String                        = "prefHapticCallNumber"
        val PREF_GYRO_CALL_NUMBER : String                          = "prefGyroCallNumber"

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
        val DB_NAME_TEMPERATURE = "temperature.realm"

        var STASTIC_CURRENT_STATE = "DAY"

        var alMeasuredData: MeasuredData? = null
        var alTemperatureData: TemperatureData? = null

        val REQUEST_ENABLE_BT = 1
        // Stops scanning after 10 seconds.
        val SCAN_PERIOD: Long = 10000
        val PERMISSION_REQUEST_COARSE_LOCATION = 1

        val HB_MEASUREMENT_DATA                       = "HB_Data"

        var nCurFunctionIndex                           = 1
        val SELECT_FUNCTION_INDEX                       = "Select_Index"

        val MAIN_FUNCTION_INDEX_HB                      = 1
        val MAIN_FUNCTION_INDEX_PRESSURE                = 2
        val MAIN_FUNCTION_INDEX_REAR                    = 3
        val MAIN_FUNCTION_INDEX_UV                      = 4
        val MAIN_FUNCTION_INDEX_GYRO                    = 5
        val MAIN_FUNCTION_INDEX_TEMPERATURE             = 6
        val MAIN_FUNCTION_INDEX_STATISTICS              = 7
        val MAIN_FUNCTION_INDEX_SETTING                 = 8


        var curYearOfDay = 2018
        var curMonthOfDay = 11
        var curDayOfDay = 16
        var curYearOfMonth = 2018
        var curMonthOfMonth = 11


        val MESSAGE_SEND_HB                             = "HBMessage"
        val MESSAGE_SEND_PRESSURE                       = "PressureMessage"
        val MESSAGE_SEND_REAR                           = "RearMessage"
        val MESSAGE_SEND_UV                             = "UVMessage"
        val MESSAGE_SEND_GYRO                           = "GyroMessage"
        val MESSAGE_SEND_TEMPERATURE                    = "TemperatureMessage"

        val ACTION_GYRO_SIGNAL                          = 1

        // Test
//        val MODULE_ADDRESS_HB                             = "98:D3:33:80:BA:7A"
//        val MODULE_ADDRESS_PRESSURE                       = "98:D3:33:80:BA:7A"
//        val MODULE_ADDRESS_REAR                           = "98:D3:A1:FD:61:3B"
//        val MODULE_ADDRESS_UV                             = "E0:E5:CF:B7:CF:0F"
//        val MODULE_ADDRESS_GYRO                           = "0C:B2:B7:46:50:FB"
//        val MODULE_ADDRESS_TEMPERATURE                    = "98:D3:A1:FD:5E:48"


        //        // ShowCase
        val MODULE_ADDRESS_HB                             = "20:15:03:27:16:49"
        val MODULE_ADDRESS_PRESSURE                       = "20:15:03:27:16:49"
        val MODULE_ADDRESS_REAR                           = "98:D3:51:FD:92:80"
        //        val MODULE_ADDRESS_UV                             = "0C:B2:B7:47:59:7F"
        val MODULE_ADDRESS_UV                             = "E8:A5:70:A4:5F:92" //sense test
        val MODULE_ADDRESS_GYRO                           = "F0:45:DA:10:B3:F9"
        val MODULE_ADDRESS_TEMPERATURE                    = "98:D3:61:FD:71:2F"

    }
}