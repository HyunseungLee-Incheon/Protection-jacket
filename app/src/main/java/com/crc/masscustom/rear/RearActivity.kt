package com.crc.masscustom.rear

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.crc.masscustom.R
import com.crc.masscustom.base.CommonUtils
import com.crc.masscustom.base.Constants
import kotlinx.android.synthetic.main.activity_rear.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rear)

        tv_toolbar_title.text = getString(R.string.str_measure_title)
        bt_toolbar_back.setOnClickListener(this)

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

        var fLeftDistance = 5.3F
        var fRightDistance = 11.8F
        var fBackDistance = 2.4F

        var nLeftRear = commonUtils.calcRearDetect(fLeftDistance)
        var nRightRear = commonUtils.calcRearDetect(fRightDistance)
        var nBackRear = commonUtils.calcRearDetect(fBackDistance)

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

    override fun onClick(v: View?) {

    }

}