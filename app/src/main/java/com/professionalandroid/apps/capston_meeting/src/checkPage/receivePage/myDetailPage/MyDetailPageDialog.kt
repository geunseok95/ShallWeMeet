package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.professionalandroid.apps.capston_meeting.R

class MyDetailPageDialog(context: Context, val listener: MyDetailDialogClicked): Dialog(context) {

    interface MyDetailDialogClicked{
        fun clickYes()
        fun clickNo()
    }

    var deleteBtn: Button? = null
    var noneDeleteBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_window_mydetail)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window?.attributes = layoutParams
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        deleteBtn = findViewById(R.id.delete)
        deleteBtn?.setOnClickListener {
            listener.clickYes()
        }
        noneDeleteBtn = findViewById(R.id.none_delete)
        noneDeleteBtn?.setOnClickListener {
            listener.clickNo()
        }
    }

}