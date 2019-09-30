package com.crc.masscustom.setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.crc.masscustom.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var tvPressureNumber: TextView
    lateinit var tvGyroNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        tv_toolbar_title.text = getString(R.string.str_setting_title)
        bt_toolbar_back.setOnClickListener(this)

        tvPressureNumber = findViewById(R.id.tv_pressure_number)
        tvGyroNumber = findViewById(R.id.tv_gyro_number)

        bt_pressure_112.setOnClickListener(this)
        bt_pressure_contact.setOnClickListener(this)
        bt_gyro_119.setOnClickListener(this)
        bt_gyro_contact.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_pressure_112 -> {
                tvPressureNumber.text = getString(R.string.str_setting_112)
            }
            R.id.bt_pressure_contact -> {
                val contactIntent = Intent(Intent.ACTION_PICK)
                contactIntent.data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                startActivityForResult(contactIntent, 0)
            }
            R.id.bt_gyro_119 -> {
                tvGyroNumber.text = getString(R.string.str_setting_119)
            }
            R.id.bt_gyro_contact -> {
                val contactIntent = Intent(Intent.ACTION_PICK)
                contactIntent.data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                startActivityForResult(contactIntent, 1)
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
                tvPressureNumber.text = number
            } else if(requestCode == 1) {
                tvGyroNumber.text = number
            }
        }
    }
}