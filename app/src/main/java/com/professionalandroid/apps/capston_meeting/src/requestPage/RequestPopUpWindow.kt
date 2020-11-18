package com.professionalandroid.apps.capston_meeting.src.requestPage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.professionalandroid.apps.capston_meeting.R

class RequestPopUpWindow(context : Context, mlistener: MyDialogOKClickedListener) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var lblDesc : TextView
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private val listener = mlistener

    fun start(message : String, success: Int) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   // 타이틀바 제거
        dlg.setContentView(R.layout.popup_window_request)     // 다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    // 다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 다이얼로그 배경 투명
        lblDesc = dlg.findViewById(R.id.message)
        lblDesc.text = message

        btnOK = dlg.findViewById(R.id.ok)
        btnOK.setOnClickListener {
            dlg.dismiss()
            listener.onOKClicked(success)
        }
        dlg.show()
    }


    interface MyDialogOKClickedListener {
        fun onOKClicked(success: Int)
    }
}