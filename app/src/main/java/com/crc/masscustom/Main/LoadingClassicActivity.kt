package com.crc.masscustom.main

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.crc.masscustom.R
import com.crc.masscustom.base.BluetoothClassicManager
import com.crc.masscustom.base.Constants
import com.crc.masscustom.bluetooth.BluetoothActivity
import com.crc.masscustom.bluetooth.BluetoothLeService
import com.crc.masscustom.finedust.FineDustActivity
import com.crc.masscustom.gas.GasActivity
import com.crc.masscustom.gyro.GyroActivity
import com.crc.masscustom.measure.HeartBeatMeasureActivity
import com.crc.masscustom.pressure.PressureActivity
import com.crc.masscustom.rear.RearActivity
import com.crc.masscustom.temperature.TemperatureActivity
import com.crc.masscustom.uv.UvActivity
import kotlinx.android.synthetic.main.activity_loading.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity

class LoadingClassicActivity : AppCompatActivity(), View.OnClickListener  {
    override fun onClick(v: View?) {
        doDiscovery()
    }

    private val mBtHandler = BluetoothHandler()
    private val mBluetoothClassicManager: BluetoothClassicManager = BluetoothClassicManager.getInstance()
    private var mIsConnected = false

    var nFunctionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_loading)

        iv_loading_text.setOnClickListener(this)

        val intent = intent
        if(intent != null) {
            nFunctionIndex = intent.getIntExtra(Constants.SELECT_FUNCTION_INDEX, Constants.MAIN_FUNCTION_INDEX_STATISTICS)
        }

        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        }

        mBluetoothClassicManager.setHandler(mBtHandler)

        // Register for broadcasts when a device is discovered
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        this.registerReceiver(mReceiver, filter)

        // Register for broadcasts when discovery has finished
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        this.registerReceiver(mReceiver, filter)


        // Register for broadcasts when a device is discovered
        filter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
        this.registerReceiver(mReceiver, filter)

        doDiscovery()

    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private fun doDiscovery() {


        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true)

        // If we're already discovering, stop it
        if (mBluetoothClassicManager.isDiscovering()) {
            mBluetoothClassicManager.cancelDiscovery()
        }

        // Request discover from BluetoothAdapter
        mBluetoothClassicManager.startDiscovery()
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


    override fun onDestroy() {
        super.onDestroy()

        // Make sure we're not doing discovery anymore
        mBluetoothClassicManager.cancelDiscovery()
        mBluetoothClassicManager.stop()
        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver)
    }



    override fun onPause() {
        super.onPause()
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
        }

    }

    private fun sendMessageToActivity(msg: String) {
        var strActionName = "NoAction"

        when(Constants.nCurFunctionIndex) {
            Constants.MAIN_FUNCTION_INDEX_HB -> {
                strActionName = Constants.MESSAGE_SEND_HB
            }
            Constants.MAIN_FUNCTION_INDEX_FINEDUST -> {
                strActionName = Constants.MESSAGE_SEND_FINEDUST
            }
            Constants.MAIN_FUNCTION_INDEX_GAS -> {
                strActionName = Constants.MESSAGE_SEND_GAS
            }
//            Constants.MAIN_FUNCTION_INDEX_PRESSURE -> {
//                strActionName = Constants.MESSAGE_SEND_PRESSURE
//            }
//            Constants.MAIN_FUNCTION_INDEX_REAR -> {
//                strActionName = Constants.MESSAGE_SEND_REAR
//            }
            Constants.MAIN_FUNCTION_INDEX_UV -> {
                strActionName = Constants.MESSAGE_SEND_UV
            }
            Constants.MAIN_FUNCTION_INDEX_GYRO -> {
                strActionName = Constants.MESSAGE_SEND_GYRO
            }
            Constants.MAIN_FUNCTION_INDEX_TEMPERATURE -> {
                strActionName = Constants.MESSAGE_SEND_TEMPERATURE
            }
        }

        val intent = Intent(strActionName)
        // You can also include some extra data.
        intent.putExtra("value", msg)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from the Intent
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                var strDeviceAddress = device.address
                Constants.strDeviceAddress = strDeviceAddress
                Log.e("eleutheria", "address : ${strDeviceAddress}")

                when(nFunctionIndex) {
                    Constants.MAIN_FUNCTION_INDEX_HB -> {
                        if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_HB)) {
                            Log.e("eleutheria", "find device HeartBeat")
                            mBluetoothClassicManager.connect(Constants.strDeviceAddress)
//                            moveMainActivity()
                        }
                    }
                    Constants.MAIN_FUNCTION_INDEX_FINEDUST -> {
                        if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_GAS)) {
                            Log.e("eleutheria", "find device FineDust")
                            mBluetoothClassicManager.connect(Constants.strDeviceAddress)
//                            moveMainActivity()
                        }
                    }
                    Constants.MAIN_FUNCTION_INDEX_GAS -> {
                        if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_GAS)) {
                            Log.e("eleutheria", "find device Gas")
                            mBluetoothClassicManager.connect(Constants.strDeviceAddress)
//                            moveMainActivity()
                        }
                    }
//                    Constants.MAIN_FUNCTION_INDEX_PRESSURE -> {
//                        if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_PRESSURE)) {
//                            Log.e("eleutheria", "find device Pressure")
//                            mBluetoothClassicManager.connect(Constants.strDeviceAddress)
////                            moveMainActivity()
//                        }
//                    }
//                    Constants.MAIN_FUNCTION_INDEX_REAR -> {
//                        if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_REAR)) {
//                            Log.e("eleutheria", "find device Rear")
//                            mBluetoothClassicManager.connect(Constants.strDeviceAddress)
////                            moveMainActivity()
//                        }
//                    }
                    Constants.MAIN_FUNCTION_INDEX_UV -> {
                        if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_GAS)) {
                            Log.e("eleutheria", "find device UV")
                            mBluetoothClassicManager.connect(Constants.strDeviceAddress)
//                            moveMainActivity()
                        }
                    }
                    Constants.MAIN_FUNCTION_INDEX_GYRO -> {
                        if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_GYRO)) {
                            Log.e("eleutheria", "find device GYRO")
                            mBluetoothClassicManager.connect(Constants.strDeviceAddress)
//                            moveMainActivity()
                        }
                    }
                    Constants.MAIN_FUNCTION_INDEX_TEMPERATURE -> {
                        if(strDeviceAddress.equals(Constants.MODULE_ADDRESS_GAS)) {
                            Log.e("eleutheria", "find device Temperature")
                            mBluetoothClassicManager.connect(Constants.strDeviceAddress)
//                            moveMainActivity()
                        }
                    }
                }
                // If it's already paired, skip it, because it's been listed already
                if (device.bondState != BluetoothDevice.BOND_BONDED) {
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                setProgressBarIndeterminateVisibility(false)
            }

            // When discovery finds a device
            if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED == action) {
                val scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1)
                val prevMode = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE, -1)
                when(scanMode) {
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> {
                        mBluetoothClassicManager.start()
                        Log.e("eleutheria", "SCAN_MODE_CONNECTABLE_DISCOVERABLE")
                    }
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE -> {
                        Log.e("eleutheria", "SCAN_MODE_CONNECTABLE")
                    }
                    BluetoothAdapter.SCAN_MODE_NONE -> {
                        // Bluetooth is not enabled
                        Log.e("eleutheria", "SCAN_MODE_NONE")
                    }
                }
            }
        }
    }

    inner class BluetoothHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                BluetoothClassicManager.MESSAGE_READ -> {
                    if (msg.obj != null) {

                        val readBuf = msg.obj as ByteArray
                        // construct a string from the valid bytes in the buffer
                        val readMessage = String(readBuf, 0, msg.arg1)
                        Log.e("eleutheria", "MESSAGE_READ : $readMessage")

                        sendMessageToActivity(readMessage)
                    }
                }
                BluetoothClassicManager.MESSAGE_STATE_CHANGE -> {
                    when(msg.arg1) {
                        BluetoothClassicManager.STATE_NONE -> {    // we're doing nothing
                            Log.e("eleutheria", "STATE_NONE")
                            mIsConnected = false
                        }
                        BluetoothClassicManager.STATE_LISTEN -> {  // now listening for incoming connections
                            Log.e("eleutheria", "STATE_LISTEN")
                            mIsConnected = false
                        }
                        BluetoothClassicManager.STATE_CONNECTING -> {  // connecting to remote
                            Log.e("eleutheria", "STATE_CONNECTING")

                        }
                        BluetoothClassicManager.STATE_CONNECTED -> {   // now connected to a remote device
                            Log.e("eleutheria", "STATE_CONNECTED")
                            mIsConnected = true
                            moveMainActivity()
                        }
                    }
                }
                BluetoothClassicManager.MESSAGE_DEVICE_NAME -> {
                    if(msg.data != null) {
                        Log.e("eleutheria", "MESSAGE_DEVICE_NAME")
                    }
                }
            }

            super.handleMessage(msg)
        }
    }
}