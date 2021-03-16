package com.crc.masscustom.main

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.crc.masscustom.R
import com.crc.masscustom.base.*
import com.crc.masscustom.finedust.FineDustActivity
import com.crc.masscustom.gas.GasActivity
import com.crc.masscustom.temperature.TemperatureActivity
import com.crc.masscustom.gyro.GyroActivity
import com.crc.masscustom.measure.HeartBeatMeasureActivity
import com.crc.masscustom.setting.SettingActivity
import com.crc.masscustom.statistics.StatisticSelActivity
import com.crc.masscustom.uv.UvActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main_grid.*
import org.jetbrains.anko.startActivity
import com.crc.masscustom.base.Constants


class MainGridActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
    View.OnClickListener {

    val realm: Realm = Realm.getDefaultInstance()

    lateinit var context: Context
    lateinit var mActivity: Activity

    internal var PERMISSIONS = arrayOf(
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.BLUETOOTH",
        "android.permission.BLUETOOTH_ADMIN",
        "android.permission.SEND_SMS",
        "android.permission.READ_PHONE_STATE",
        "android.permission.ACCESS_NOTIFICATION_POLICY",
        "android.permission.READ_CONTACTS",
        "android.permission.CALL_PHONE",
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_grid)

        tv_toolbar_title.text = getString(R.string.str_main_title)
        tv_toolbar_title.setOnClickListener(this)

        context = applicationContext
        mActivity = this

        val mainIconList = arrayListOf<Int>(
            R.drawable.main_icon_heartbeat,
            R.drawable.main_icon_finedust,
            R.drawable.main_icon_gas,
            R.drawable.main_icon_uv,
            R.drawable.main_icon_gyro,
            R.drawable.main_icon_temperature,
            R.drawable.main_icon_statistics,
            R.drawable.main_icon_set_up
        )

        val listAdapter = MainGridAdapter(this, mainIconList)
        gvMainList.adapter = listAdapter
        gvMainList.setOnItemClickListener(this)

        var preferences: SharedPreferences = getSharedPreferences(Constants.SHARED_PREF_SEUPDATA, Context.MODE_PRIVATE)

        Constants.strHapticNumber = preferences.getString(Constants.PREF_HAPTIC_CALL_NUMBER, Constants.strHapticNumber)
        Constants.strGyroNumber = preferences.getString(Constants.PREF_GYRO_CALL_NUMBER, Constants.strGyroNumber)

        var commonUtils = CommonUtils()
        var curDate = commonUtils.getCurrentDate()

        Constants.curYearOfDay = curDate[0].toInt()
        Constants.curMonthOfDay = curDate[1].toInt()
        Constants.curDayOfDay = curDate[2].toInt()
        Constants.curYearOfMonth = curDate[0].toInt()
        Constants.curMonthOfMonth = curDate[1].toInt()

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //퍼미션 상태 확인
            if (!hasPermissions(PERMISSIONS)) {
                //퍼미션 허가 안되어있다면 사용자에게 요청
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE)
            }
//            if (!notificationManager.isNotificationPolicyAccessGranted) {
//                mActivity.runOnUiThread {
//                    Toast.makeText(
//                        context,
//                        context.getString(R.string.str_toast_turn_off_do_not_disturb),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
//                startActivity(intent)
//            }
        }
    }

    private fun hasPermissions(permissions: Array<String>): Boolean {
        var result: Int
        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (perms in permissions) {
            result = ContextCompat.checkSelfPermission(this, perms)
            if (result == PackageManager.PERMISSION_DENIED) {
                //허가 안된 퍼미션 발견
                return false
            }
        }
        //모든 퍼미션이 허가되었음
        return true
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.e("eleutheria", "position : " + position)
        when(position) {
            0 -> { // heartbeat
                Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_HB
//                startActivity<LoadingActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_HB)
//                startActivity<HeartBeatMeasureActivity>()
                startActivity<LoadingResultActivity>()
            }
            1 -> { // finedust
                Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_FINEDUST
                startActivity<LoadingActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_FINEDUST)
//                startActivity<FineDustActivity>()
            }
            2 -> { // gas
                Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_GAS
                startActivity<LoadingActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_GAS)
//                startActivity<GasActivity>()
            }
//            1 -> { // pressure
//                Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_PRESSURE
////                startActivity<LoadingClassicActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_PRESSURE)
//                startActivity<PressureActivity>()
//            }
//            2 -> { // rear
//                Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_REAR
////                startActivity<LoadingClassicActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_REAR)
//                startActivity<RearActivity>()
//            }
            3 -> { // uv
                Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_UV
                startActivity<LoadingActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_UV)
//                startActivity<UvActivity>()
            }
            4 -> { // gyro
                Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_GYRO
//                startActivity<LoadingActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_GYRO)
                startActivity<GyroActivity>()
            }
            5 -> { // temperature
                Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_TEMPERATURE
                startActivity<LoadingActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_TEMPERATURE)
//                startActivity<TemperatureActivity>()
            }
            6 -> { // statistic
                startActivity<StatisticSelActivity>()
            }
            7 -> { // setting
//                Constants.nCurFunctionIndex = Constants.MAIN_FUNCTION_INDEX_SETTING
//                startActivity<LoadingActivity>(Constants.SELECT_FUNCTION_INDEX to Constants.MAIN_FUNCTION_INDEX_SETTING)
                startActivity<SettingActivity>()
            }
        }
    }
    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.tv_toolbar_title -> {
                makeTestData()
            }
        }

    }

    private fun makeTestData() {

        val commonUtils = CommonUtils()
        var nRandom = 0
        var nSecond = 0


        // HeartBeat
//        Log.e("eleutheria", "makeTestData HeartBeat")
//        for(ntime in 0 until 24) {
//            for(nAgain in 0 until 3) {
//
//                val random = Random()
//
//                when (ntime) {
//                    0 -> {
//                        nRandom = random.nextInt(50 - 30) + 30
//                    }
//                    1 -> {
//                        nRandom = random.nextInt(55 - 25) + 25
//                    }
//                    2 -> {
//                        nRandom = random.nextInt(50 - 40) + 40
//                    }
//                    3 -> {
//                        nRandom = random.nextInt(50 - 45) + 45
//                    }
//                    4 -> {
//                        nRandom = random.nextInt(50 - 30) + 30
//                    }
//                    5 -> {
//                        nRandom = random.nextInt(50 - 30) + 30
//                    }
//                    6 -> {
//                        nRandom = random.nextInt(60 - 35) + 35
//                    }
//                    7 -> {
//                        nRandom = random.nextInt(85 - 65) + 65
//                    }
//                    8 -> {
//                        nRandom = random.nextInt(100 - 80) + 80
//                    }
//                    9 -> {
//                        nRandom = random.nextInt(100 - 80) + 80
//                    }
//                    10 -> {
//                        nRandom = random.nextInt(105 - 75) + 75
//                    }
//                    11 -> {
//                        nRandom = random.nextInt(130 - 110) + 110
//                    }
//                    12 -> {
//                        nRandom = random.nextInt(140 - 120) + 120
//                    }
//                    13 -> {
//                        nRandom = random.nextInt(135 - 115) + 115
//                    }
//                    14 -> {
//                        nRandom = random.nextInt(95 - 70) + 70
//                    }
//                    15 -> {
//                        nRandom = random.nextInt(105 - 85) + 85
//                    }
//                    16 -> {
//                        nRandom = random.nextInt(150 - 90) + 90
//                    }
//                    17 -> {
//                        nRandom = random.nextInt(105 - 80) + 80
//                    }
//                    18 -> {
//                        nRandom = random.nextInt(95 - 75) + 75
//                    }
//                    19 -> {
//                        nRandom = random.nextInt(95 - 70) + 70
//                    }
//                    20 -> {
//                        nRandom = random.nextInt(90 - 60) + 60
//                    }
//                    21 -> {
//                        nRandom = random.nextInt(80 - 60) + 60
//                    }
//                    22 -> {
//                        nRandom = random.nextInt(80 - 50) + 50
//                    }
//                    23 -> {
//                        nRandom = random.nextInt(60 - 40) + 40
//                    }
//                }
////                nRandom = random.nextInt(132 - 83) + 83
//
//                realm.beginTransaction()
//
//                var newId: Long = 1
//                if (realm.where(DBHeartBeatModel::class.java).max("id") != null) {
//                    newId = realm.where(DBHeartBeatModel::class.java).max("id") as Long + 1
//                }
//                var storedData: MeasuredData =
//                    commonUtils.makeTestData(2020, 9, 28, ntime, 53, 27, nRandom, Constants.USER_STATUS_NORMAL)
//                var insertData = realm.createObject(DBHeartBeatModel::class.java, newId)
//                insertData.year = storedData.nYear
//                insertData.month = storedData.nMonth
//                insertData.day = storedData.nDay
//                insertData.hour = storedData.nHour
//                insertData.minute = storedData.nMinute
//                insertData.second = storedData.nSecond
//                insertData.heartbeat = storedData.nHeartbeat
//                insertData.status = storedData.nStatus
//
//                realm.commitTransaction()
//            }
//        }

        // Temperature
//        Log.e("eleutheria", "makeTestData Temperature")
//        for(nHour in 0 until 24) {
//            for(nMinute in 0 until 60) {
//                for(nSecRepeat in 0 until 3) {
//                    for(nAgain in 0 until 3) {
//
//                        val random = Random()
//
//                        when (nHour) {
//                            0 -> {
//                                nRandom = random.nextInt(25 - 23) + 23
//                            }
//                            1 -> {
//                                nRandom = random.nextInt(25 - 23) + 23
//                            }
//                            2 -> {
//                                nRandom = random.nextInt(25 - 23) + 23
//                            }
//                            3 -> {
//                                nRandom = random.nextInt(25 - 23) + 23
//                            }
//                            4 -> {
//                                nRandom = random.nextInt(25 - 23) + 23
//                            }
//                            5 -> {
//                                nRandom = random.nextInt(25 - 23) + 23
//                            }
//                            6 -> {
//                                nRandom = random.nextInt(26 - 24) + 24
//                            }
//                            7 -> {
//                                nRandom = random.nextInt(26 - 22) + 22
//                            }
//                            8 -> {
//                                nRandom = random.nextInt(15 - 13) + 13
//                            }
//                            9 -> {
//                                nRandom = random.nextInt(17 - 15) + 15
//                            }
//                            10 -> {
//                                nRandom = random.nextInt(19 - 17) + 17
//                            }
//                            11 -> {
//                                nRandom = random.nextInt(21 - 19) + 19
//                            }
//                            12 -> {
//                                nRandom = random.nextInt(23 - 21) + 21
//                            }
//                            13 -> {
//                                nRandom = random.nextInt(22 - 20) + 20
//                            }
//                            14 -> {
//                                nRandom = random.nextInt(23 - 21) + 21
//                            }
//                            15 -> {
//                                nRandom = random.nextInt(24 - 22) + 22
//                            }
//                            16 -> {
//                                nRandom = random.nextInt(24 - 22) + 22
//                            }
//                            17 -> {
//                                nRandom = random.nextInt(22 - 20) + 20
//                            }
//                            18 -> {
//                                nRandom = random.nextInt(21 - 19) + 19
//                            }
//                            19 -> {
//                                nRandom = random.nextInt(20 - 18) + 18
//                            }
//                            20 -> {
//                                nRandom = random.nextInt(19 - 17) + 17
//                            }
//                            21 -> {
//                                nRandom = random.nextInt(24 - 22) + 22
//                            }
//                            22 -> {
//                                nRandom = random.nextInt(24 - 22) + 22
//                            }
//                            23 -> {
//                                nRandom = random.nextInt(24 - 22) + 22
//                            }
//                        }
////                        nRandom = random.nextInt(24 - 15) + 15
//
//                        if(nSecRepeat == 0) {
//                            if (nAgain == 0) {
//                                nSecond = 7
//                            } else if (nAgain == 1) {
//                                nSecond = 12
//                            } else {
//                                nSecond = 18
//                            }
//                        } else if (nSecRepeat == 1) {
//                            if (nAgain == 0) {
//                                nSecond = 23
//                            } else if (nAgain == 1) {
//                                nSecond = 31
//                            } else {
//                                nSecond = 38
//                            }
//                        } else {
//                            if (nAgain == 0) {
//                                nSecond = 43
//                            } else if (nAgain == 1) {
//                                nSecond = 49
//                            } else {
//                                nSecond = 55
//                            }
//                        }
//
//                        realm.beginTransaction()
//
//                        val curDate = commonUtils.getCurrentDate()
//                        val mResultDate: CurrentDate = CurrentDate()
//                        mResultDate.nYear = 2020
//                        mResultDate.nMonth = 9
//                        mResultDate.nDay = 28
//                        mResultDate.nHour = nHour
//                        mResultDate.nMinute = nMinute
//                        mResultDate.nSecond = nSecond
//                        var newId: Long = 1
//                        if (realm.where(DBTemperatureModel::class.java).max("id") != null) {
//                            newId =
//                                realm.where(DBTemperatureModel::class.java).max("id") as Long + 1
//                        }
//                        val storedData: TemperatureData =
//                            commonUtils.storeTemperatureData(mResultDate, nRandom)
//                        val insertData = realm.createObject(DBTemperatureModel::class.java, newId)
//                        insertData.year = storedData.nYear
//                        insertData.month = storedData.nMonth
//                        insertData.day = storedData.nDay
//                        insertData.hour = storedData.nHour
//                        insertData.minute = storedData.nMinute
//                        insertData.second = storedData.nSecond
//                        insertData.temperature = storedData.nTemperature
//
//                        realm.commitTransaction()
//                    }
//                }
//            }
//        }

    }

    companion object {

        //여기서부턴 퍼미션 관련 메소드
        private val PERMISSION_REQUEST_COARSE_LOCATION = 456
        internal val PERMISSIONS_REQUEST_CODE = 1000
    }
}