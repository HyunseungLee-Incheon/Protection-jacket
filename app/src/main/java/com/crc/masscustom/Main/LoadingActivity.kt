package com.crc.masscustom.main

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.bluetooth.BluetoothActivity
import com.crc.masscustom.bluetooth.BluetoothLeService
import com.crc.masscustom.bluetooth.SampleGattAttributes
import com.crc.masscustom.finedust.FineDustActivity
import com.crc.masscustom.gas.GasActivity
import com.crc.masscustom.gyro.GyroActivity
import com.crc.masscustom.measure.HeartBeatMeasureActivity
import com.crc.masscustom.measure.HeartBeatResultActivity
import com.crc.masscustom.pressure.PressureActivity
import com.crc.masscustom.rear.RearActivity
import com.crc.masscustom.setting.SettingActivity
import com.crc.masscustom.temperature.TemperatureActivity
import com.crc.masscustom.uv.UvActivity
import io.realm.log.RealmLog.debug
import kotlinx.android.synthetic.main.activity_loading.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class LoadingActivity : AppCompatActivity(), View.OnClickListener {

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mScanning: Boolean = false
    private var mHandler: Handler? = null

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000

    private var mDeviceName: String? = null
    private var mDeviceAddress: String? = null
    private var mGattCharacteristics: ArrayList<ArrayList<BluetoothGattCharacteristic>>? = ArrayList()
    private var mConnected = false
    private var mNotifyCharacteristic: BluetoothGattCharacteristic? = null

    private val LIST_NAME = "NAME"
    private val LIST_UUID = "UUID"

    var nFunctionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_loading)

        iv_loading_text.setOnClickListener(this)

        val intent = intent
        if(intent != null) {
            nFunctionIndex = intent.getIntExtra(Constants.SELECT_FUNCTION_INDEX, Constants.MAIN_FUNCTION_INDEX_STATISTICS)
        }

        mHandler = Handler()

        if (PermissionChecker.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        }

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.str_bluetooth_not_supported, Toast.LENGTH_SHORT).show()
            return
        }

        val gattServiceIntent = Intent(this, BluetoothLeService::class.java)
        bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)

//        moveMainActivity()
    }

    override fun onBackPressed() {
        startActivity(intentFor<MainGridActivity>().clearTask().newTask())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> when (grantResults) {
                intArrayOf(PackageManager.PERMISSION_GRANTED) -> {
                    Log.d("ScanDeviceActivity", "onRequestPermissionsResult(PERMISSION_GRANTED)")
//                    bluetoothLeScanner.startScan(bleScanner)
                }
                else -> {
                    Log.d("ScanDeviceActivity", "onRequestPermissionsResult(not PERMISSION_GRANTED)")
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onResume() {
        super.onResume()

        if (!mBluetoothAdapter!!.isEnabled) {
            if (!mBluetoothAdapter!!.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT)
            }
        }

        // Initializes list view adapter.

        scanLeDevice(true)

        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter())
        if (mBluetoothLeService != null) {
            val result = mBluetoothLeService!!.connect(mDeviceAddress)
            Log.d("eleutheria", "Connect request result=" + result)
        }
    }

    override fun onPause() {
        super.onPause()
        scanLeDevice(false)
        unregisterReceiver(mGattUpdateReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()

        unbindService(mServiceConnection)
        mBluetoothLeService = null

        mScanning = false
        bluetoothLeScanner.stopScan(mLeScanCallback)
    }

    private fun scanLeDevice(enable: Boolean) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler!!.postDelayed(Runnable {
                mScanning = false

                bluetoothLeScanner.stopScan(mLeScanCallback)
            }, Constants.SCAN_PERIOD)

            mScanning = true
            bluetoothLeScanner.startScan(mLeScanCallback)
        } else {
            mScanning = false
            bluetoothLeScanner.stopScan(mLeScanCallback)
        }
//        mBluetoothAdapter!!.startDiscovery()

//        if (enable) {
//            // Stops scanning after a pre-defined scan period.
//            mHandler!!.postDelayed({
//                mScanning = false
//                mBluetoothAdapter!!.stopLeScan(mLeScanCallback)
//                invalidateOptionsMenu()
//            }, Constants.SCAN_PERIOD)
//
//            mScanning = true
//            mBluetoothAdapter!!.startLeScan(mLeScanCallback)
//        } else {
//            mScanning = false
//            mBluetoothAdapter!!.stopLeScan(mLeScanCallback)
//        }
    }

    private val mLeScanCallback = object : ScanCallback() {
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.e("eleutheria", "onScanFailed")
        }


        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            var strDeviceAddress = result!!.device.address
            Log.e("eleutheria", "address : ${strDeviceAddress}")

            when(nFunctionIndex) {
                Constants.MAIN_FUNCTION_INDEX_HB -> {
                    if(strDeviceAddress == Constants.MODULE_ADDRESS_HB) {
                        Log.e("eleutheria", "find device HeartBeat")
                        mBluetoothLeService!!.connect(strDeviceAddress)
                    }
                }
                Constants.MAIN_FUNCTION_INDEX_FINEDUST -> {
                    if(strDeviceAddress == Constants.MODULE_ADDRESS_GAS) {
                        Log.e("eleutheria", "find device FineDust")
                        mBluetoothLeService!!.connect(strDeviceAddress)
                    }
                }
                Constants.MAIN_FUNCTION_INDEX_GAS -> {
                    if(strDeviceAddress == Constants.MODULE_ADDRESS_GAS) {
                        Log.e("eleutheria", "find device Gas")
                        mBluetoothLeService!!.connect(strDeviceAddress)
                    }
                }
//                Constants.MAIN_FUNCTION_INDEX_PRESSURE -> {
//                    if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_PRESSURE)) {
//                        Log.e("eleutheria", "find device Pressure")
//                        mBluetoothLeService!!.connect(strDeviceAddress)
//                    }
//                }
//                Constants.MAIN_FUNCTION_INDEX_REAR -> {
//                    if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_REAR)) {
//                        Log.e("eleutheria", "find device Rear")
//                        mBluetoothLeService!!.connect(strDeviceAddress)
//                    }
//                }
                Constants.MAIN_FUNCTION_INDEX_UV -> {
                    if(strDeviceAddress == Constants.MODULE_ADDRESS_GAS) {
                        Log.e("eleutheria", "find device UV")
                        mBluetoothLeService!!.connect(strDeviceAddress)
                    }
                }
                Constants.MAIN_FUNCTION_INDEX_GYRO -> {
                    if(strDeviceAddress == Constants.MODULE_ADDRESS_GYRO) {
                        Log.e("eleutheria", "find device GYRO")
                        mBluetoothLeService!!.connect(strDeviceAddress)
                    }
                }
                Constants.MAIN_FUNCTION_INDEX_TEMPERATURE -> {
                    if(strDeviceAddress == Constants.MODULE_ADDRESS_GAS) {
                        Log.e("eleutheria", "find device Temperature")
                        mBluetoothLeService!!.connect(strDeviceAddress)
                    }
                }
                Constants.MAIN_FUNCTION_INDEX_SETTING -> {
                    if(strDeviceAddress == Constants.MODULE_ADDRESS_GAS) {
                        Log.e("eleutheria", "find device HeartBeatResult")
                        mBluetoothLeService!!.connect(strDeviceAddress)
                    }
                }
                Constants.MAIN_FUNCTION_INDEX_HB_RESULT -> {
                    if(strDeviceAddress == Constants.MODULE_ADDRESS_GAS) {
                        Log.e("eleutheria", "find device HeartBeatResult")
                        mBluetoothLeService!!.connect(strDeviceAddress)
                    }
                }
            }

//            mLeBluetoothListAdapter!!.addDevice(result!!.device)
//            mLeBluetoothListAdapter!!.notifyDataSetChanged()
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            Log.e("eleutheria", "onBatchScanResults")
        }
    }

    private val bluetoothLeScanner: BluetoothLeScanner
        get() {
            val bluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            val bluetoothAdapter = bluetoothManager.adapter
            return bluetoothAdapter.bluetoothLeScanner
        }

//    private val mLeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
//        runOnUiThread {
////            mLeBluetoothListAdapter!!.addDevice(device)
////            mLeBluetoothListAdapter!!.notifyDataSetChanged()
//        }
//    }

    // Bluetooth Connect

    // Code to manage Service lifecycle.
    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
            mBluetoothLeService = (service as BluetoothLeService.LocalBinder).service
            if (!mBluetoothLeService!!.initialize()) {
                Log.e("eleutheria", "Unable to initialize Bluetooth")
                finish()
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService!!.connect(mDeviceAddress)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBluetoothLeService = null
        }
    }

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothLeService.ACTION_GATT_CONNECTED == action) {
                mConnected = true
                debug("eleutheria : ACTION_GATT_CONNECTED")

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED == action) {
                mConnected = false
                debug("eleutheria : ACTION_GATT_DISCONNECTED")
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED == action) {
                // Show all the supported services and characteristics on the user interface.
                addGattServices(mBluetoothLeService!!.supportedGattServices)
                activeNotification()
                debug("eleutheria : ACTION_GATT_SERVICES_DISCOVERED")
                moveMainActivity()
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE == action) {
                parsingData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA))
                debug("eleutheria : ACTION_DATA_AVAILABLE")
            }
        }
    }

    private fun parsingData(data: String?) {
        if (data != null) {
//            Log.e("eleutheria", "data : ${data}")

            //System.out.println(data);
        }
    }

    private fun addGattServices(gattServices: List<BluetoothGattService>?) {
        if (gattServices == null) return
        var uuid: String? = null
        val unknownServiceString = resources.getString(R.string.str_bluetooth_unknown_service)
        val unknownCharaString = resources.getString(R.string.str_bluetooth_unknown_characteristic)
        val gattServiceData = ArrayList<HashMap<String, String>>()
        val gattCharacteristicData = ArrayList<ArrayList<HashMap<String, String>>>()
        mGattCharacteristics = ArrayList<ArrayList<BluetoothGattCharacteristic>>()

        // Loops through available GATT Services.
        for (gattService in gattServices) {
            val currentServiceData = HashMap<String, String>()
            uuid = gattService.uuid.toString()
            Log.e("eleutheria", "uuid : " + uuid)
            println(uuid)
            currentServiceData.put(
                LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString)
            )
            currentServiceData.put(LIST_UUID, uuid)
            gattServiceData.add(currentServiceData)

            val gattCharacteristicGroupData = ArrayList<HashMap<String, String>>()
            val gattCharacteristics = gattService.characteristics
            val charas = ArrayList<BluetoothGattCharacteristic>()

            // Loops through available Characteristics.
            for (gattCharacteristic in gattCharacteristics) {
                charas.add(gattCharacteristic)
                val currentCharaData = HashMap<String, String>()
                uuid = gattCharacteristic.uuid.toString()
                println(uuid)
                println(currentCharaData)

                currentCharaData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString)
                )
                currentCharaData.put(LIST_UUID, uuid)
                gattCharacteristicGroupData.add(currentCharaData)
            }
            mGattCharacteristics!!.add(charas)
            gattCharacteristicData.add(gattCharacteristicGroupData)
        }
    }

    private fun activeNotification() {
        if (mGattCharacteristics != null) {
            val characteristic = mGattCharacteristics!![2][0]
            val charaProp = characteristic.properties
            if (charaProp or BluetoothGattCharacteristic.PROPERTY_READ > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    mBluetoothLeService!!.setCharacteristicNotification(
                        mNotifyCharacteristic!!, false)
                    mNotifyCharacteristic = null
                }
                mBluetoothLeService!!.readCharacteristic(characteristic)
            }
            if (charaProp or BluetoothGattCharacteristic.PROPERTY_NOTIFY > 0) {
                mNotifyCharacteristic = characteristic
                mBluetoothLeService!!.setCharacteristicNotification(
                    characteristic, true)
            }
        }
    }

    override fun onClick(v: View?) {
        scanLeDevice(true)
    }

    private fun moveMainActivity() {

        when(nFunctionIndex) {
            Constants.MAIN_FUNCTION_INDEX_HB -> {
                startActivity<HeartBeatMeasureActivity>()
            }
            Constants.MAIN_FUNCTION_INDEX_FINEDUST -> {
                startActivity<FineDustActivity>()
            }
            Constants.MAIN_FUNCTION_INDEX_GAS -> {
                startActivity<GasActivity>()
            }
//            Constants.MAIN_FUNCTION_INDEX_PRESSURE -> {
//                startActivity<PressureActivity>()
//            }
//            Constants.MAIN_FUNCTION_INDEX_REAR -> {
//                startActivity<RearActivity>()
//            }
            Constants.MAIN_FUNCTION_INDEX_UV -> {
                startActivity<UvActivity>()
            }
            Constants.MAIN_FUNCTION_INDEX_GYRO -> {
                startActivity<GyroActivity>()
            }
            Constants.MAIN_FUNCTION_INDEX_TEMPERATURE -> {
                startActivity<TemperatureActivity>()
            }
            Constants.MAIN_FUNCTION_INDEX_SETTING -> {
                startActivity<SettingActivity>()
            }
            Constants.MAIN_FUNCTION_INDEX_HB_RESULT -> {
                startActivity<HeartBeatResultActivity>()
            }
        }

    }

    companion object {
        private val TAG = BluetoothActivity::class.java!!.getSimpleName()


        var mBluetoothLeService: BluetoothLeService? = null

        private fun makeGattUpdateIntentFilter(): IntentFilter {
            val intentFilter = IntentFilter()
            intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED)
            intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED)
            intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED)
            intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE)
            return intentFilter
        }
    }


}