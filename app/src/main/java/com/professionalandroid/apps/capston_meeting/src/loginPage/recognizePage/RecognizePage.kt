package com.professionalandroid.apps.capston_meeting.src.loginPage.recognizePage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.BaseActivity
import com.professionalandroid.apps.capston_meeting.src.loginPage.recognizePage.interfaces.RecognizePageView
import com.professionalandroid.apps.capston_meeting.src.registerPage.RegisterPage
import kotlinx.android.synthetic.main.activity_recognize_page.*


class RecognizePage : BaseActivity(), RecognizePageView {

    lateinit var mRecognizePageService: RecognizePageService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recognize_page)

        mRecognizePageService = RecognizePageService(this, this)

        recognize_phone_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val s = p0.toString()
                if(s.length == 11){
                    recognize_check_btn.apply {
                        setTextColor(Color.WHITE)
                        Log.d("test", recognize_phone_number.text.toString())
                        background = ContextCompat.getDrawable(context!!, R.drawable.round_button)
                        setOnClickListener {
                            mRecognizePageService.getCode(recognize_phone_number.text.toString())
                        }
                    }
                }
                else{
                    recognize_check_btn.apply {
                        setTextColor(Color.parseColor("#20294b"))
                        background = ContextCompat.getDrawable(context!!, R.drawable.empty_button)
                        setOnClickListener {}
                    }
                }
            }

        })

        recognize_resend.setOnClickListener {
            mRecognizePageService.getCode(recognize_phone_number.text.toString())
        }

        recognize_confirm_btn.setOnClickListener {
            mRecognizePageService.confirmCode(recognize_number.text.toString())
        }

    }

    override fun getCode() {
        recognize_check_btn.visibility = View.GONE
        recognize_number_layout.visibility = View.VISIBLE
        recognize_confirm_btn.visibility = View.VISIBLE
    }

    override fun confirmCode() {
        val email = intent.getStringExtra("email")
        val gender = intent.getStringExtra("gender")
        val phone = recognize_phone_number.text.toString()

        val intent = Intent(this, RegisterPage::class.java).apply {
            putExtra("email", email)
            putExtra("gender", gender)
            putExtra("phone", phone)
        }
        startActivity(intent)
    }
}