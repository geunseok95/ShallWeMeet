package com.professionalandroid.apps.capston_meeting.src.requestPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.RegisterPage

class BottomSheetDialog() : BottomSheetDialogFragment() {
    private var mbottombutton_listener: BottomsheetbuttonItemSelectedInterface? = null

    constructor(bottombutton_listener: RequestPage) : this() {
        mbottombutton_listener = bottombutton_listener
    }
    constructor(bottombutton_listener: RegisterPage) : this() {
        mbottombutton_listener = bottombutton_listener

    }

    interface BottomsheetbuttonItemSelectedInterface{
        fun bottombutton_listener(v: View)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
         container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false)
        mbottombutton_listener?.bottombutton_listener(view)
        return view
    }



}