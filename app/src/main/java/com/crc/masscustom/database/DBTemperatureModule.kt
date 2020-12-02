package com.crc.masscustom.database

class DBTemperatureModule {
    fun provideTemperatureManager(): DBTemperatureManager {
        return DBTemperatureManager()
    }
}