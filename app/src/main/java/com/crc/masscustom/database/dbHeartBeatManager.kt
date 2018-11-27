package com.crc.masscustom.database

import com.crc.masscustom.base.Constants
import com.crc.masscustom.base.MeasuredData
import io.realm.Realm
import io.realm.RealmConfiguration

class dbHeartBeatManager {

    val realm: Realm by lazy {
        val config = RealmConfiguration.Builder()
            .name(Constants.DB_NAME_HEARTBEAT)
            .schemaVersion(2)
            .build()
        Realm.getInstance(config)
    }

    fun find(id: Long): dbHeartBeatModel? {
        return realm.where(dbHeartBeatModel::class.java).equalTo("id", id).findFirst()
    }

    fun findAll(): List<dbHeartBeatModel> {
        return realm.where(dbHeartBeatModel::class.java).findAll()
    }

    fun insert(storedData: MeasuredData) {
        realm.beginTransaction()
        var newId: Long = 1
        if(realm.where(dbHeartBeatModel::class.java).max("id") != null) {
            newId = realm.where(dbHeartBeatModel::class.java).max("id") as Long + 1
        }

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

    }
}