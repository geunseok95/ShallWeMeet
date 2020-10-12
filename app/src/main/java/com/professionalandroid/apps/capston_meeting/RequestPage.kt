package com.professionalandroid.apps.capston_meeting

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makeramen.roundedimageview.RoundedDrawable
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.img_num
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_list
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.fragment_detail_page.view.*
import kotlinx.android.synthetic.main.fragment_request_page.view.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime

class RequestPage : Fragment(), BottomSheetDialog.BottomsheetbuttonItemSelectedInterface{


    lateinit var mrequest_img : RoundedImageView
    lateinit var mbottomsheetdialog: BottomSheetDialog
    lateinit var retrofitService: ConnectRetrofit

    override fun onAttach(context: Context) {
        super.onAttach(context)
        retrofitService = ConnectRetrofit(context)
    }

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


        val request_btn = view.request_button.apply {
            setOnClickListener {
                val input: HashMap<String, Any> = HashMap()
                val connect_server = retrofitService.retrofitService()
                input["title"] = view.request_title.text.toString()
                input["img1"] = bitmaptoByteArray(view.request_img1)
                input["img2"] = bitmaptoByteArray(view.request_img2)
                input["img3"] = bitmaptoByteArray(view.request_img3)
                input["keyword"] = view.request_keyword.text.toString()
                input["location"] = view.request_location.text.toString()
                input["num_type"] = view.request_num_type.text.toString()
                input["gender"] = "여자"
                input["user"] = user(45)

                connect_server.sendBoard(input).enqueue(object:
                    Callback<board> {
                    override fun onFailure(call: Call<board>, t: Throwable) {
                        Log.d("test","서버연결 실패")
                    }

                    override fun onResponse(call: Call<board>, response: Response<board>) {
                        val board:board = response.body()!!
                        Log.d("test", "서버 연결 성공")
                        Log.d("test", board.title)
                    }
                })

                (activity as MainActivity).close_fragment(this@RequestPage)
            }
        }
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


    fun bitmaptoByteArray(img: RoundedImageView): ByteArray{
        val stream = ByteArrayOutputStream()
        val drawable = img.drawable
        (drawable as RoundedDrawable).sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()
        return byteArray
    }

}