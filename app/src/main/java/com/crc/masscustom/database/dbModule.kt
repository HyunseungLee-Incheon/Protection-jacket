package com.crc.masscustom.database

class dbModule {

    fun provideHeartBeatManager(): dbHeartBeatManager {
        return dbHeartBeatManager()
    }
}