package com.professionalandroid.apps.capston_meeting

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import com.makeramen.roundedimageview.RoundedDrawable
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.img_num
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_File_list
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_list
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.fragment_apply_filter.view.*
import kotlinx.android.synthetic.main.fragment_detail_page.view.*
import kotlinx.android.synthetic.main.fragment_request_page.view.*
import okhttp3.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime

class RequestPage : Fragment(), BottomSheetDialog.BottomsheetbuttonItemSelectedInterface{


    lateinit var mrequest_img : ImageView
    lateinit var mbottomsheetdialog: BottomSheetDialog
    lateinit var retrofitService: ConnectRetrofit
    lateinit var spinneradapter: ArrayAdapter<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        retrofitService = ConnectRetrofit(context)
        val items = resources.getStringArray(R.array.location)
        spinneradapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, items)

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



        view.request_location.adapter = spinneradapter
        view.request_location.prompt = "지역을 선택하세요"

        request_Image_list.clear()
        request_Image_list.add(view.request_img1)
        request_Image_list.add(view.request_img2)
        request_Image_list.add(view.request_img3)

        val uri = Uri.parse("android.resource://com.professionalandroid.apps.capston_meeting/drawable/happy.jpg")
        request_Image_File_list.add(uri)
        request_Image_File_list.add(uri)
        request_Image_File_list.add(uri)

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
                val data: HashMap<String, RequestBody> = HashMap()
                val connect_server = retrofitService.retrofitService()

//
//                data["title"] = view.request_title.text.toString()
//                data["keyword"] = view.request_keyword.text.toString()
//                data["location"] = view.request_location.selectedItem.toString()
//                data["num_type"] = view.request_num_type.text.toString()
//                data["gender"] = "여자"
//                data["user"] =  "22"
//                data["age"] = "23"
//

                data["title"] = RequestBody.create(MediaType.parse("text/plain"),view.request_title.text.toString())
                data["keyword"] =  RequestBody.create(MediaType.parse("text/plain"),view.request_keyword.text.toString())
                data["location"] = RequestBody.create(MediaType.parse("text/plain"), view.request_location.selectedItem.toString())
                data["num_type"] =  RequestBody.create(MediaType.parse("text/plain"),view.request_num_type.text.toString())
                data["gender"] = RequestBody.create(MediaType.parse("text/plain"),"여자")
                data["age"] =  RequestBody.create(MediaType.parse("text/plain"),"23")

                val originalFile1 = File(request_Image_File_list[0].path)
                val originalFile2 = File(request_Image_File_list[1].path)
                val originalFile3 = File(request_Image_File_list[2].path)

                Log.d("test", originalFile1.toString())

                val filePart1: RequestBody = RequestBody.create(
                    MediaType.parse("image/*"),
                    originalFile1
                )

                Log.d("test", filePart1.toString())


                val filePart2: RequestBody = RequestBody.create(
                    MediaType.parse("image/*"),
                    originalFile2
                )

                val filePart3: RequestBody = RequestBody.create(
                    MediaType.parse("image/*"),
                    originalFile3
                )

                val file1: MultipartBody.Part = MultipartBody.Part.createFormData("img1", originalFile1.name, filePart1)
                val file2: MultipartBody.Part = MultipartBody.Part.createFormData("img2", originalFile2.name, filePart2)
                val file3: MultipartBody.Part = MultipartBody.Part.createFormData("img3", originalFile3.name, filePart3)

                Log.d("test", file1.toString())

                connect_server.sendBoard(file1, file2, file3, data).enqueue(object:
                    Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("test","서버연결 실패")
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        //val board:String = response.body()!!
                        Log.d("test", "서버 연결 성공")
                        //Log.d("test", board)
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


}