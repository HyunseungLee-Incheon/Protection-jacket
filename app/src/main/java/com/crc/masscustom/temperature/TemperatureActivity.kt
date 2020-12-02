package com.crc.masscustom.temperature

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import com.crc.masscustom.base.CurrentDate
import com.crc.masscustom.base.TemperatureData
import com.crc.masscustom.database.DBTemperatureModel
import com.crc.masscustom.main.MainGridActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_temperature.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class TemperatureActivity : AppCompatActivity(), View.OnClickListener {

    val realm: Realm = Realm.getDefaultInstance()
    val commonUtils = CommonUtils()

    lateinit var tvTemperatureText: TextView
    lateinit var ivTemperatureBg: ImageView
    var nTemperature = 15
    var strReceiveData = ""

//    private val mBtHandler = BluetoothHandler()
//    private val mBluetoothClassicManager: BluetoothClassicManager = BluetoothClassicManager.getInstance()
//    private var mIsConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_temperature)

        tv_toolbar_title.text = getString(R.string.str_temperature_title)
        bt_toolbar_back.setOnClickListener(this)

        tvTemperatureText = findViewById(R.id.tv_temperature_text)
        ivTemperatureBg = findViewById(R.id.iv_temperature_bg)

//        mBluetoothClassicManager.setHandler(mBtHandler)
//
//        // Register for broadcasts when a device is discovered
//        var filter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
//        this.registerReceiver(mReceiver, filter)
//
//
//        mBluetoothClassicManager.connect(Constants.strDeviceAddress)

        displayTemperature(nTemperature)
    }

    private fun displayTemperature(nTemperature: Int) {
        if(nTemperature <= Constants.TEMPERATURE_COLD) {
            var strCurTemperature = nTemperature.toString() +  getString(R.string.str_main_default_temperature)
            tvTemperatureText.text = strCurTemperature
            ivTemperatureBg.setImageResource(R.drawable.temperature_cold)
        } else if(nTemperature <= Constants.TEMPERATURE_GOOD) {
            var strCurTemperature = nTemperature.toString() +  getString(R.string.str_main_default_temperature)
            tvTemperatureText.text = strCurTemperature
            ivTemperatureBg.setImageResource(R.drawable.temperature_good)
        } else {
            var strCurTemperature = nTemperature.toString() +  getString(R.string.str_main_default_temperature)
            tvTemperatureText.text = strCurTemperature
            ivTemperatureBg.setImageResource(R.drawable.temperature_hot)
        }
    }

    private fun saveTemperature(nTemperature: Int) {
        val curDate = commonUtils.getCurrentDate()
        val mResultDate : CurrentDate = CurrentDate()
        mResultDate.nYear = curDate[0].toInt()
        mResultDate.nMonth = curDate[1].toInt()
        mResultDate.nDay = curDate[2].toInt()
        mResultDate.nHour = curDate[3].toInt()
        mResultDate.nMinute = curDate[4].toInt()
        mResultDate.nSecond = curDate[5].toInt()

        realm.beginTransaction()

        var newId: Long = 1
        if(realm.where(DBTemperatureModel::class.java).max("id") != null) {
            newId = realm.where(DBTemperatureModel::class.java).max("id") as Long + 1
        }
        val storedData: TemperatureData = commonUtils.storeTemperatureData(mResultDate, nTemperature)
        val insertData = realm.createObject(DBTemperatureModel::class.java, newId)
        insertData.year = storedData.nYear
        insertData.month = storedData.nMonth
        insertData.day = storedData.nDay
        insertData.hour = storedData.nHour
        insertData.minute = storedData.nMinute
        insertData.second = storedData.nSecond
        insertData.temperature = storedData.nTemperature

        realm.commitTransaction()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(intentFor<MainGridActivity>().newTask().clearTop())
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter(Constants.MESSAGE_SEND_TEMPERATURE))
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.hasExtra("value")) {
                val message = intent!!.getStringExtra("value")


                if(message.contains("\r\n")) {
                    strReceiveData += message

                    val arData = strReceiveData.split(" : ")

                    if(arData.size > 1) {
                        val arTempData = arData[1].split(".")
//                        val arTempData = strTempData[0]

                        nTemperature = arTempData[0].toInt()
                        saveTemperature(nTemperature)
                        displayTemperature(nTemperature)
                    }
                    strReceiveData = ""

                } else {
                    strReceiveData += message
                }
            }
        }

    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
//    private val mReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val action = intent.action
//
//            // When discovery finds a device
//            if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED == action) {
//                val scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1)
//                val prevMode = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE, -1)
//                when(scanMode) {
//                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> {
//                        mBluetoothClassicManager.start()
//                        Log.e("eleutheria", "SCAN_MODE_CONNECTABLE_DISCOVERABLE")
//                    }
//                    BluetoothAdapter.SCAN_MODE_CONNECTABLE -> {
//                        Log.e("eleutheria", "SCAN_MODE_CONNECTABLE")
//                    }
//                    BluetoothAdapter.SCAN_MODE_NONE -> {
//                        // Bluetooth is not enabled
//                        Log.e("eleutheria", "SCAN_MODE_NONE")
//                    }
//                }
//            }
//        }
//    }

//    inner class BluetoothHandler : Handler() {
//        override fun handleMessage(msg: Message) {
//            when (msg.what) {
//                BluetoothClassicManager.MESSAGE_READ -> {
//                    if (msg.obj != null) {
//
//                        val readBuf = msg.obj as ByteArray
//                        // construct a string from the valid bytes in the buffer
//                        val readMessage = String(readBuf, 0, msg.arg1)
//                        Log.e("eleutheria", "MESSAGE_READ : $readMessage")
//                    }
//                }
//                BluetoothClassicManager.MESSAGE_STATE_CHANGE -> {
//                    when(msg.arg1) {
//                        BluetoothClassicManager.STATE_NONE -> {    // we're doing nothing
//                            Log.e("eleutheria", "STATE_NONE")
//                            mIsConnected = false
//                        }
//                        BluetoothClassicManager.STATE_LISTEN -> {  // now listening for incoming connections
//                            Log.e("eleutheria", "STATE_LISTEN")
//                            mIsConnected = false
//                        }
//                        BluetoothClassicManager.STATE_CONNECTING -> {  // connecting to remote
//                            Log.e("eleutheria", "STATE_CONNECTING")
//
//                        }
//                        BluetoothClassicManager.STATE_CONNECTED -> {   // now connected to a remote device
//                            Log.e("eleutheria", "STATE_CONNECTED")
//                            mIsConnected = true
//                        }
//                    }
//                }
//                BluetoothClassicManager.MESSAGE_DEVICE_NAME -> {
//                    if(msg.data != null) {
//                        Log.e("eleutheria", "MESSAGE_DEVICE_NAME")
//                    }
//                }
//            }
//
//            super.handleMessage(msg)
//        }
//    }
}