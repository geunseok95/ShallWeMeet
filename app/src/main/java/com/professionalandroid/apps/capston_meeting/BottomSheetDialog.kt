package com.professionalandroid.apps.capston_meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*


class BottomSheetDialog:BottomSheetDialogFragment() {

    var bottom_sheet_dialog_camera: RoundedImageView? = null
    var bottom_sheet_dialog_gallery: RoundedImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
         container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false)
        bottom_sheet_dialog_camera = view.bottom_sheet_dialog_camera
        bottom_sheet_dialog_gallery = view.bottom_sheet_dialog_gallery


        return view
    }



}