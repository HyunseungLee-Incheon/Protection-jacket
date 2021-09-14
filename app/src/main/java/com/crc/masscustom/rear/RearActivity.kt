package com.crc.masscustom.rear

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import com.crc.masscustom.main.MainGridActivity

class RearActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var ivLeftFour: ImageView
    lateinit var ivLeftThree: ImageView
    lateinit var ivLeftTwo: ImageView
    lateinit var ivLeftOne: ImageView

    lateinit var ivRightFour: ImageView
    lateinit var ivRightThree: ImageView
    lateinit var ivRightTwo: ImageView
    lateinit var ivRightOne: ImageView

    lateinit var ivBackFour: ImageView
    lateinit var ivBackThree: ImageView
    lateinit var ivBackTwo: ImageView
    lateinit var ivBackOne: ImageView

    var strReceiveData : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rear)

        var tvToolbarTitle : TextView = findViewById(R.id.tv_toolbar_title)
        tvToolbarTitle.text = getString(R.string.str_rear_title)

        var btToolbarBack : Button = findViewById(R.id.bt_toolbar_back)
        btToolbarBack.setOnClickListener(this)

        val commonUtils = CommonUtils()

        ivLeftFour = findViewById<ImageView>(R.id.iv_left_four)
        ivLeftThree = findViewById<ImageView>(R.id.iv_left_three)
        ivLeftTwo = findViewById<ImageView>(R.id.iv_left_two)
        ivLeftOne = findViewById<ImageView>(R.id.iv_left_one)

        ivRightFour = findViewById<ImageView>(R.id.iv_right_four)
        ivRightThree = findViewById<ImageView>(R.id.iv_right_three)
        ivRightTwo = findViewById<ImageView>(R.id.iv_right_two)
        ivRightOne = findViewById<ImageView>(R.id.iv_right_one)

        ivBackFour = findViewById<ImageView>(R.id.iv_back_four)
        ivBackThree = findViewById<ImageView>(R.id.iv_back_three)
        ivBackTwo = findViewById<ImageView>(R.id.iv_back_two)
        ivBackOne = findViewById<ImageView>(R.id.iv_back_one)

        val fLeftDistance = 5.3F
        val fRightDistance = 11.8F
        val fBackDistance = 2.4F

        val nLeftRear = commonUtils.calcRearDetect(fLeftDistance)
        val nRightRear = commonUtils.calcRearDetect(fRightDistance)
        val nBackRear = commonUtils.calcRearDetect(fBackDistance)

        displayLeftRear(nLeftRear)
        displayRightRear(nRightRear)
        displayBackRear(nBackRear)

    }

    private fun displayLeftRear(nLeftIndex: Int) {
        when(nLeftIndex) {
            Constants.REAR_INDEX_ZERO -> {
                ivLeftFour.visibility = View.INVISIBLE
                ivLeftThree.visibility = View.INVISIBLE
                ivLeftTwo.visibility = View.INVISIBLE
                ivLeftOne.visibility = View.INVISIBLE
            }
            Constants.REAR_INDEX_ONE -> {
                ivLeftFour.visibility = View.INVISIBLE
                ivLeftThree.visibility = View.INVISIBLE
                ivLeftTwo.visibility = View.INVISIBLE
                ivLeftOne.visibility = View.VISIBLE
            }
            Constants.REAR_INDEX_TWO -> {
                ivLeftFour.visibility = View.INVISIBLE
                ivLeftThree.visibility = View.INVISIBLE
                ivLeftTwo.visibility = View.VISIBLE
                ivLeftOne.visibility = View.VISIBLE
            }
            Constants.REAR_INDEX_THREE -> {
                ivLeftFour.visibility = View.INVISIBLE
                ivLeftThree.visibility = View.VISIBLE
                ivLeftTwo.visibility = View.VISIBLE
                ivLeftOne.visibility = View.VISIBLE
            }
            Constants.REAR_INDEX_FOUR -> {
                ivLeftFour.visibility = View.VISIBLE
                ivLeftThree.visibility = View.VISIBLE
                ivLeftTwo.visibility = View.VISIBLE
                ivLeftOne.visibility = View.VISIBLE
            }
        }

    }

    private fun displayRightRear(nRightIndex: Int) {
        when(nRightIndex) {
            Constants.REAR_INDEX_ZERO -> {
                ivRightFour.visibility = View.INVISIBLE
                ivRightThree.visibility = View.INVISIBLE
                ivRightTwo.visibility = View.INVISIBLE
                ivRightOne.visibility = View.INVISIBLE
            }
            Constants.REAR_INDEX_ONE -> {
                ivRightFour.visibility = View.INVISIBLE
                ivRightThree.visibility = View.INVISIBLE
                ivRightTwo.visibility = View.INVISIBLE
                ivRightOne.visibility = View.VISIBLE
            }
            Constants.REAR_INDEX_TWO -> {
                ivRightFour.visibility = View.INVISIBLE
                ivRightThree.visibility = View.INVISIBLE
                ivRightTwo.visibility = View.VISIBLE
                ivRightOne.visibility = View.VISIBLE
            }
            Constants.REAR_INDEX_THREE -> {
                ivRightFour.visibility = View.INVISIBLE
                ivRightThree.visibility = View.VISIBLE
                ivRightTwo.visibility = View.VISIBLE
                ivRightOne.visibility = View.VISIBLE
            }
            Constants.REAR_INDEX_FOUR -> {
                ivRightFour.visibility = View.VISIBLE
                ivRightThree.visibility = View.VISIBLE
                ivRightTwo.visibility = View.VISIBLE
                ivRightOne.visibility = View.VISIBLE
            }
        }
    }

    private fun displayBackRear(nBackIndex: Int) {
        when(nBackIndex) {
            Constants.REAR_INDEX_ZERO -> {
                ivBackFour.visibility = View.INVISIBLE
                ivBackThree.visibility = View.INVISIBLE
                ivBackTwo.visibility = View.INVISIBLE
                ivBackOne.visibility = View.INVISIBLE
            }
            Constants.REAR_INDEX_ONE -> {
                ivBackFour.visibility = View.INVISIBLE
                ivBackThree.visibility = View.INVISIBLE
                ivBackTwo.visibility = View.INVISIBLE
                ivBackOne.visibility = View.VISIBLE
            }
            Constants.REAR_INDEX_TWO -> {
                ivBackFour.visibility = View.INVISIBLE
                ivBackThree.visibility = View.INVISIBLE
                ivBackTwo.visibility = View.VISIBLE
                ivBackOne.visibility = View.VISIBLE
            }
            Constants.REAR_INDEX_THREE -> {
                ivBackFour.visibility = View.INVISIBLE
                ivBackThree.visibility = View.VISIBLE
                ivBackTwo.visibility = View.VISIBLE
                ivBackOne.visibility = View.VISIBLE
            }
            Constants.REAR_INDEX_FOUR -> {
                ivBackFour.visibility = View.VISIBLE
                ivBackThree.visibility = View.VISIBLE
                ivBackTwo.visibility = View.VISIBLE
                ivBackOne.visibility = View.VISIBLE
            }
        }
    }

    private fun sendSMS() {
        val strPhoneNumber = Constants.strGyroNumber

        val smsManager = SmsManager.getDefault()

//        val message = "Gyro Action Message!! "
        val message = "비상 상황입니다! 구조가 필요합니다!"
        val strFirstString = strPhoneNumber.substring(0, 1)

        if(strFirstString.equals("0")) {
            Log.e("eleutheria", "strPhoneNumber : $strPhoneNumber")
            smsManager.sendTextMessage(strPhoneNumber, null, message, null, null)
        }
    }

    private fun sendCall() {
        val strPhoneNumber = Constants.strGyroNumber

        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$strPhoneNumber"))
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_toolbar_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainGridActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter(Constants.MESSAGE_SEND_REAR))
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.hasExtra("value")) {
                val message = intent!!.getStringExtra("value")

                val commonUtils = CommonUtils()

                if (message != null) {
                    if(message.contains("\r\n")) {
                        strReceiveData += message
                        Log.e("eleutheria", "strReceiveData : $strReceiveData")
                        var arData = strReceiveData.split(".")

                        if(arData.size > 2) {
                            val strLeft = arData[0]
                            val strBack = arData[1]
                            val strRight = arData[2]

                            val arDataL = strLeft.split(" ")
                            val arDataB = strBack.split(" ")
                            val arDataR = strRight.split(" ")

                            val fLeftDistance = arDataL[1].toFloat()
                            val fBackDistance = arDataB[1].toFloat()
                            val fRightDistance = arDataR[1].toFloat()

            //                        Log.e("eleutheria", "fLeftDistance : $fLeftDistance, fBackDistance : $fBackDistance, fRightDistance : $fRightDistance")
                            val nLeftRear = commonUtils.calcRearDetect(fLeftDistance / 100)
                            val nBackRear = commonUtils.calcRearDetect(fBackDistance / 100)
                            val nRightRear = commonUtils.calcRearDetect(fRightDistance / 100)

                            displayLeftRear(nLeftRear)
                            displayRightRear(nRightRear)
                            displayBackRear(nBackRear)

                            if((fLeftDistance <= 0.3F) || (fRightDistance <= 0.3F) || (fBackDistance <= 0.3F)) {
                                sendCall()
                                sendSMS()
                            }
                        }

                        if(arData.size > 8) {
                            var fLeftDistance = arData[3].toFloat()
                            var fBackDistance = arData[5].toFloat()
                            var fRightDistance = arData[7].toFloat()


                        }
                        strReceiveData = ""
                    } else {
                        strReceiveData += message
                    }
                }
            }
        }
    }
}