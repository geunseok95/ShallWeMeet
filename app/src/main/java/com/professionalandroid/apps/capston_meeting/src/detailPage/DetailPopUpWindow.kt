package com.professionalandroid.apps.capston_meeting.src.detailPage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.professionalandroid.apps.capston_meeting.R
import kotlinx.android.synthetic.main.popup_window_detail.*

class DetailPopUpWindow(context : Context, mlistener: MyDialogOKClickedListener) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnPay : Button
    private lateinit var btnNonePay : Button
    private val listener = mlistener

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   // 타이틀바 제거
        dlg.setContentView(R.layout.popup_window_detail)     // 다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    // 다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 다이얼로그 배경 투명

        btnPay = dlg.pay
        btnPay.setOnClickListener {
            dlg.dismiss()
            listener.onPayClicked()
        }
        btnNonePay = dlg.none_pay
        btnNonePay.setOnClickListener {
            dlg.dismiss()
            listener.onNonePayClicked()
        }
        dlg.show()
    }


    interface MyDialogOKClickedListener {
        fun onPayClicked()
        fun onNonePayClicked()
    }
}