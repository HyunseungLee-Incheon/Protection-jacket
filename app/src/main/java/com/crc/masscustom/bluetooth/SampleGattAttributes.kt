package com.crc.masscustom.bluetooth

import java.util.HashMap

object SampleGattAttributes {
    var attributes: HashMap<String, String> = HashMap()
    var CLIENT_CHARACTERISTIC_CONFIG =              "00002902-0000-1000-8000-00805f9b34fb"
    var HEART_RATE_AND_BATTERY_MEASUREMENT =        "0000ffe0-0000-1000-8000-00805f9b34fb"

    // service
    var SERVICE_HEART_BEAT_MEASUREMENT                              = "0000180a-0000-1000-8000-00805f9b34fb"
    var SERVICE_PRESSURE_MEASUREMENT                                = "0000180a-0000-1000-8000-00805f9b34fb"
    var SERVICE_REAR_MEASUREMENT                                    = "00001801-0000-1000-8000-00805f9b34fb"
    var SERVICE_TEMPERATURE_MEASUREMENT                             = "0000180a-0000-1000-8000-00805f9b34fb"

    //    var SERVICE_UV_MEASUREMENT                                      = "0000dfb0-0000-1000-8000-00805f9b34fb"
//    var SERVICE_GYRO_MEASUREMENT                                    = "0000dfb0-0000-1000-8000-00805f9b34fb"
    var SERVICE_BEETLE_MEASUREMENT                                    = "0000dfb0-0000-1000-8000-00805f9b34fb"


    //characteristic
    var HEART_BEAT_MEASUREMENT                              = "00001801-0000-1000-8000-00805f9b34fb"
    var PRESSURE_MEASUREMENT                                = "0000180a-0000-1000-8000-00805f9b34fb"
    var REAR_MEASUREMENT                                    = "00001801-0000-1000-8000-00805f9b34fb"
    var TEMPERATURE_MEASUREMENT                             = "0000180a-0000-1000-8000-00805f9b34fb"

    //    var UV_MEASUREMENT                                      = "0000dfb1-0000-1000-8000-00805f9b34fb"
//    var GYRO_MEASUREMENT                                    = "0000dfb1-0000-1000-8000-00805f9b34fb"
    var BEETLE_MEASUREMENT                                    = "0000dfb1-0000-1000-8000-00805f9b34fb"

    init {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service")
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service")
        // Sample Characteristics.
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String")


        // Using unknown GATT profile, must debug other end
        attributes.put("19B10000-E8F2-537E-4F6C-D104768A1214", "ioTank")
        attributes.put(HEART_BEAT_MEASUREMENT, "Heart Rate Measurement")
        attributes.put(PRESSURE_MEASUREMENT, "Pressure Measurement")
        attributes.put(REAR_MEASUREMENT, "Rear Measurement")
//        attributes.put(UV_MEASUREMENT, "UV Measurement")
//        attributes.put(GYRO_MEASUREMENT, "GYRO Measurement")
        attributes.put(BEETLE_MEASUREMENT, "BEETLE Measurement")
        attributes.put(TEMPERATURE_MEASUREMENT, "Temperature Measurement")
    }


    fun lookup(uuid: String, defaultName: String): String {
        val name = attributes.get(uuid)
        return name ?: defaultName
    }
}