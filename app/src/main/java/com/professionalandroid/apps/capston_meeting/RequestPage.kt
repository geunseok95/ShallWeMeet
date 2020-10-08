package com.professionalandroid.apps.capston_meeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.fragment_request_page.view.*

class RequestPage : Fragment() {

    var bottomsheetdialog: BottomSheetDialog? = null
    var request_img: RoundedImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_request_page, container, false)
        bottomsheetdialog = BottomSheetDialog()
        request_img = view.request_img

        request_img?.setOnClickListener{
            bottomsheetdialog?.show((activity as MainActivity).supportFragmentManager, "bottom_sheet_dialog")
        }

        return view
    }


}