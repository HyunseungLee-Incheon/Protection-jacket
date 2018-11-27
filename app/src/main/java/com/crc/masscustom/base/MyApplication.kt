package com.crc.masscustom.base

import android.app.Application
import android.support.v4.app.AppComponentFactory
import com.crc.masscustom.database.dbHeartBeatManager
import io.realm.Realm

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}