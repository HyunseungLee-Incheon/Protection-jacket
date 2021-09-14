package com.crc.masscustom.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.crc.masscustom.R
import com.crc.masscustom.base.Constants

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    private var settings: SharedPreferences? = null
    lateinit var tvPressureNumber: TextView
    lateinit var tvGyroNumber: TextView
    lateinit var tvEmergencyNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        var tvToolbarTitle : TextView = findViewById(R.id.tv_toolbar_title)
        tvToolbarTitle.text = getString(R.string.str_setting_title)

        var btToolbarBack : Button = findViewById(R.id.bt_toolbar_back)
        btToolbarBack.setOnClickListener(this)

        settings = getSharedPreferences(Constants.SHARED_PREF_SEUPDATA, Context.MODE_PRIVATE)


        tvPressureNumber = findViewById(R.id.tv_pressure_number)
        tvGyroNumber = findViewById(R.id.tv_gyro_number)
        tvEmergencyNumber = findViewById(R.id.tv_emergency_number)


        tvPressureNumber.text = Constants.strHapticNumber
        tvGyroNumber.text = Constants.strGyroNumber
        tvEmergencyNumber.text = Constants.strEmergencyNumber

        val btPressure112 : Button = findViewById(R.id.bt_pressure_112)
        btPressure112.setOnClickListener(this)

        val btPressureContact : Button = findViewById(R.id.bt_pressure_contact)
        btPressureContact.setOnClickListener(this)

        val btGyro119 : Button = findViewById(R.id.bt_gyro_119)
        btGyro119.setOnClickListener(this)

        val btGyroContact : Button = findViewById(R.id.bt_gyro_contact)
        btGyroContact.setOnClickListener(this)

        val btEmergency119 : Button = findViewById(R.id.bt_emergency_119)
        btEmergency119.setOnClickListener(this)

        val btEmergencyContact : Button = findViewById(R.id.bt_emergency_contact)
        btEmergencyContact.setOnClickListener(this)

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