package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.GlideApp
import kotlinx.android.synthetic.main.layout_sender.*

class ReceivePageUserDialog(context: Context, val listener: DialogClicked, val mSender_image: String, val mSender_nickname: String, val mSender_age: String, val mSender_location1: String, val mSender_location2: String, val senderId: Long, val sender_status: Boolean, val boardId: Long)
    : Dialog(context) {

    interface DialogClicked{
        fun accept(senderId: Long, sender_status: Boolean, boardId: Long)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sender)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window?.attributes = layoutParams
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        GlideApp.with(context)
            .load(mSender_image)
            .centerCrop()
            .into(sender_request_image)
        sender_request_nickname.text = mSender_nickname
        sender_request_age.text = mSender_age
        sender_request_location1.text = mSender_location1
        sender_request_location2.text = mSender_location2
        sender_request_submit_btn.setOnClickListener {
            listener.accept(senderId, sender_status, boardId)
        }
    }

}