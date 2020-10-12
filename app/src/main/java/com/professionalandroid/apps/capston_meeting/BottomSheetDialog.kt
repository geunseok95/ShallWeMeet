package com.professionalandroid.apps.capston_meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.makeramen.roundedimageview.RoundedImageView


class BottomSheetDialog(
    bottombutton_listener: BottomsheetbuttonItemSelectedInterface
    ) : BottomSheetDialogFragment() {

    interface BottomsheetbuttonItemSelectedInterface{
        fun bottombutton_listener(v: View)
    }

    private var mbottombutton_listener: BottomsheetbuttonItemSelectedInterface? =
        bottombutton_listener

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