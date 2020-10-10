package com.professionalandroid.apps.capston_meeting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.img_num
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_list
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.fragment_request_page.view.*

class RequestPage : Fragment(), BottomSheetDialog.BottomsheetbuttonItemSelectedInterface{


    lateinit var mrequest_img : RoundedImageView
    lateinit var mbottomsheetdialog: BottomSheetDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_request_page, container, false)

        request_Image_list.clear()
        request_Image_list.add(view.request_img1)
        request_Image_list.add(view.request_img2)
        request_Image_list.add(view.request_img3)

        val request_img1 = view.request_img1.apply {
            setOnClickListener {
                mrequest_img = this
                img_num = 0

                mbottomsheetdialog = BottomSheetDialog(this@RequestPage)
                mbottomsheetdialog.show((activity as MainActivity).supportFragmentManager, "bottom_sheet_dialog")} }
        val request_img2 = view.request_img2.apply {
            setOnClickListener {
                mrequest_img = this
                img_num = 1
                mbottomsheetdialog = BottomSheetDialog(this@RequestPage)
                mbottomsheetdialog.show((activity as MainActivity).supportFragmentManager, "bottom_sheet_dialog")} }
        val requesr_img3 = view.request_img3.apply {
            setOnClickListener {
                mrequest_img = this
                img_num = 2
                mbottomsheetdialog = BottomSheetDialog(this@RequestPage)
                mbottomsheetdialog.show((activity as MainActivity).supportFragmentManager, "bottom_sheet_dialog")} }



        return view
    }



    override fun bottombutton_listener(v: View) {
        val bottom_sheet_dialog_camera = v.bottom_sheet_dialog_camera
        val bottom_sheet_dialog_gallery = v.bottom_sheet_dialog_gallery

        bottom_sheet_dialog_gallery.setOnClickListener {
            (activity as MainActivity).getPhotoFromMyGallary()

        }

        bottom_sheet_dialog_camera.setOnClickListener {
            (activity as MainActivity).takeCapture()
        }

    }



}