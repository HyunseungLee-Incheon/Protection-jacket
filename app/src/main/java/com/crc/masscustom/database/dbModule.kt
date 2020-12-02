package com.crc.masscustom.database

class DBModule {

    fun provideHeartBeatManager(): DBHeartBeatManager {
        return DBHeartBeatManager()
    }
}