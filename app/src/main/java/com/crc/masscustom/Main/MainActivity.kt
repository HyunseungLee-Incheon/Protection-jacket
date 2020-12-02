package com.crc.masscustom.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.crc.masscustom.measure.HeartBeatMeasureActivity
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import com.crc.masscustom.battery.BatteryActivity
import com.crc.masscustom.database.DBHeartBeatModel
import com.crc.masscustom.statistics.StatisticActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_grid.*
import org.jetbrains.anko.startActivity
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    val realm: Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val commonUtils = CommonUtils()

        //commonUtils.printScreenInfo()
        tvDeviceName.text = Constants.strDeviceName

        val titleList = arrayListOf<String>(
            getString(R.string.str_main_list_heartbeat),
            getString(R.string.str_main_list_statistic),
            getString(R.string.str_main_list_setting)
        )

        val listAdapter = MainListAdapter(this, titleList)
        lvMainList.adapter = listAdapter
        lvMainList.setOnItemClickListener(this)

//        saveTestData()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position) {
            0 -> {
                startActivity<HeartBeatMeasureActivity>()
            }
            1 -> {
                startActivity<StatisticActivity>()
            }
            2 -> {
                startActivity<BatteryActivity>()
            }
        }
    }

    private fun saveTestData() {

        val random = Random()
        realm.beginTransaction()

        var newId: Long = 1
        for(j in 1..30) {
            for (i in 8..20) {
                if (realm.where(DBHeartBeatModel::class.java).max("id") != null) {
                    newId = realm.where(DBHeartBeatModel::class.java).max("id") as Long + 1
                }

                val insertData = realm.createObject(DBHeartBeatModel::class.java, newId)
                insertData.year = 2018
                insertData.month = 11
                insertData.day = j
                insertData.hour = i
                insertData.minute = random.nextInt(59)
                insertData.second = random.nextInt(59)
                insertData.heartbeat = random.nextInt(148) + 89
                insertData.status = 0
            }
        }

        realm.commitTransaction()
    }
}
