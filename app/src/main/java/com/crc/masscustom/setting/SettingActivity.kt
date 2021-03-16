package com.crc.masscustom.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.LoadingActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    private var settings: SharedPreferences? = null
    lateinit var tvPressureNumber: TextView
    lateinit var tvGyroNumber: TextView
    lateinit var tvEmergencyNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        tv_toolbar_title.text = getString(R.string.str_setting_title)
        bt_toolbar_back.setOnClickListener(this)

        settings = getSharedPreferences(Constants.SHARED_PREF_SEUPDATA, Context.MODE_PRIVATE)


        tvPressureNumber = findViewById(R.id.tv_pressure_number)
        tvGyroNumber = findViewById(R.id.tv_gyro_number)
        tvEmergencyNumber = findViewById(R.id.tv_emergency_number)


        tvPressureNumber.text = Constants.strHapticNumber
        tvGyroNumber.text = Constants.strGyroNumber
        tvEmergencyNumber.text = Constants.strEmergencyNumber

        bt_pressure_112.setOnClickListener(this)
        bt_pressure_contact.setOnClickListener(this)
        bt_gyro_119.setOnClickListener(this)
        bt_gyro_contact.setOnClickListener(this)
        bt_emergency_119.setOnClickListener(this)
        bt_emergency_contact.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_pressure_112 -> {
                Constants.strHapticNumber = getString(R.string.str_setting_112)
                tvPressureNumber.text = Constants.strHapticNumber

                val editor = settings!!.edit()
                editor.putString(Constants.PREF_HAPTIC_CALL_NUMBER, Constants.strHapticNumber)
                editor.apply()
            }
            R.id.bt_pressure_contact -> {
                val contactIntent = Intent(Intent.ACTION_PICK)
                contactIntent.data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                startActivityForResult(contactIntent, 0)
            }
            R.id.bt_gyro_119 -> {
                Constants.strGyroNumber = getString(R.string.str_setting_119)
                tvGyroNumber.text = Constants.strGyroNumber

                val editor = settings!!.edit()
                editor.putString(Constants.PREF_GYRO_CALL_NUMBER, Constants.strGyroNumber)
                editor.apply()
            }
            R.id.bt_gyro_contact -> {
                val contactIntent = Intent(Intent.ACTION_PICK)
                contactIntent.data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                startActivityForResult(contactIntent, 1)
            }
            R.id.bt_emergency_119 -> {
                Constants.strEmergencyNumber = getString(R.string.str_setting_119)
                tvEmergencyNumber.text = Constants.strEmergencyNumber

                val editor = settings!!.edit()
                editor.putString(Constants.PREF_EMERGENCY_CALL_NUMBER, Constants.strEmergencyNumber)
                editor.apply()
            }
            R.id.bt_emergency_contact -> {
                val contactIntent = Intent(Intent.ACTION_PICK)
                contactIntent.data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                startActivityForResult(contactIntent, 1)
//                LoadingActivity.mBluetoothLeService!!.writeTimeCharacteristic(et_heartbeat.text.toString())
            }
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {

            val cursor = contentResolver.query(
                data!!.getData()!!,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ), null, null, null
            )
            cursor!!.moveToFirst()
            val name = cursor.getString(0)        //이름 얻어오기
            val number = cursor.getString(1)

            if(requestCode == 0) {
                Constants.strHapticNumber = number
                tvPressureNumber.text = Constants.strHapticNumber

                val editor = settings!!.edit()
                editor.putString(Constants.PREF_HAPTIC_CALL_NUMBER, Constants.strHapticNumber)
                editor.apply()

            } else if(requestCode == 1) {
                Constants.strEmergencyNumber = number
                tvEmergencyNumber.text = Constants.strEmergencyNumber

                val editor = settings!!.edit()
                editor.putString(Constants.PREF_EMERGENCY_CALL_NUMBER, Constants.strEmergencyNumber)
                editor.apply()
            }
        }
    }
}