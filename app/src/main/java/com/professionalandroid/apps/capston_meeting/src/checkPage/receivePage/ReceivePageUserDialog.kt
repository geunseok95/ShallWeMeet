package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.GlideApp

class ReceivePageUserDialog(context : Context, mlistener: DialogClicked) {

    interface DialogClicked{
        fun accept(senderId: Long, sender_status: Boolean, boardId: Long)
    }

    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var sender_request_image: RoundedImageView
    private lateinit var sender_request_nickName : TextView
    private lateinit var sender_request_age : TextView
    private lateinit var sender_request_location1 : TextView
    private lateinit var sender_request_location2 : TextView
    private lateinit var sender_request_submit_btn: Button

    private val listener = mlistener

    fun start(context: Context, senderId: Long, sender_status: Boolean, boardId: Long, mSender_image: String, mSender_nickname: String, mSender_age: String, mSender_location1: String, mSender_location2: String) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   // 타이틀바 제거
        dlg.setContentView(R.layout.layout_sender)     // 다이얼로그에 사용할 xml 파일을 불러옴
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 다이얼로그 배경 투명

        sender_request_image = dlg.findViewById(R.id.sender_request_image)
        sender_request_nickName = dlg.findViewById(R.id.sender_request_nickname)
        sender_request_age = dlg.findViewById(R.id.sender_request_age)
        sender_request_location1 = dlg.findViewById(R.id.sender_request_location1)
        sender_request_location2 = dlg.findViewById(R.id.sender_request_location2)
        sender_request_submit_btn = dlg.findViewById(R.id.sender_request_submit_btn)

        GlideApp.with(context)
            .load(mSender_image)
            .centerCrop()
            .into(sender_request_image)
        sender_request_nickName.text = mSender_nickname
        sender_request_age.text = mSender_age
        sender_request_location1.text = mSender_location1
        sender_request_location2.text = mSender_location2

        sender_request_submit_btn.setOnClickListener {
            dlg.dismiss()
            listener.accept(senderId, sender_status, boardId)
        }
        dlg.show()
    }

}
