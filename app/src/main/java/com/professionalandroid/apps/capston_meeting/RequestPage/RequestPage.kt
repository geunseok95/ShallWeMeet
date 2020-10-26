package com.professionalandroid.apps.capston_meeting.requestPage

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.professionalandroid.apps.capston_meeting.MainActivity
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.img_num
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_File_list
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_list
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.fragment_request_page.*
import kotlinx.android.synthetic.main.fragment_request_page.view.*
import okhttp3.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import java.io.File

class RequestPage : Fragment(),
    BottomSheetDialog.BottomsheetbuttonItemSelectedInterface, RequestPopUpWindow.MyDialogOKClickedListener {

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

        // location
        view.request_location.adapter = spinneradapter
        view.request_location.prompt = "지역을 선택하세요"

        view.request_img1.clipToOutline = true
        view.request_img2.clipToOutline = true
        view.request_img3.clipToOutline = true

        request_Image_list.clear()
        request_Image_list.add(view.request_img1)
        request_Image_list.add(view.request_img2)
        request_Image_list.add(view.request_img3)

        val uri = Uri.parse("android.resource://com.professionalandroid.apps.capston_meeting/drawable/happy.jpg")
        request_Image_File_list.clear()
        request_Image_File_list.add(uri)
        request_Image_File_list.add(uri)
        request_Image_File_list.add(uri)

        view.request_img1.apply {
            setOnClickListener {
                img_num = 0
                mbottomsheetdialog =
                    BottomSheetDialog(
                        this@RequestPage
                    )
                mbottomsheetdialog.show((activity as MainActivity).supportFragmentManager, "bottom_sheet_dialog")} }
        view.request_img2.apply {
            setOnClickListener {
                img_num = 1
                mbottomsheetdialog =
                    BottomSheetDialog(
                        this@RequestPage
                    )
                mbottomsheetdialog.show((activity as MainActivity).supportFragmentManager, "bottom_sheet_dialog")} }
        view.request_img3.apply {
            setOnClickListener {
                img_num = 2
                mbottomsheetdialog =
                    BottomSheetDialog(
                        this@RequestPage
                    )
                mbottomsheetdialog.show((activity as MainActivity).supportFragmentManager, "bottom_sheet_dialog")} }

        view.request_button.apply {

            setOnClickListener {

                if (view.request_title.text.isNotEmpty() && view.request_num_type.text.isNotEmpty() && view.request_tag1.text.isNotEmpty() && view.request_tag2.text.isNotEmpty() && view.request_tag3.text.isNotEmpty()
                ) {
                    val popup = RequestPopUpWindow(context, this@RequestPage)
                    popup.start("미팅을 만들까요?", 1)

                }
                else{
                    val popup = RequestPopUpWindow(context, this@RequestPage)
                    popup.start("내용을 모두 입력해주세요", 0)
                }
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

    override fun onOKClicked(success:Int) {
        if(success == 1){
            val data: HashMap<String, RequestBody> = HashMap()
            val connect_server = retrofitService.retrofitService()

            data["title"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_title.text.toString()
            )
            data["location"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_location.selectedItem.toString()
            )
            data["num_type"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_num_type.text.toString()
            )
            data["gender"] = RequestBody.create(
                MediaType.parse("text/plain"),
                "여자")
            data["tag1"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_tag1.text.toString()
            )
            data["tag2"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_tag2.text.toString()
            )
            data["tag3"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_tag3.text.toString()
            )
            data["average_age"] = RequestBody.create(
                MediaType.parse("text/plain"),
                average_age.text.toString()
            )
            data["user"] = RequestBody.create(
                MediaType.parse("text/plain"),
                user
            )

            val originalFile1 = File(request_Image_File_list[0].path!!)
            val originalFile2 = File(request_Image_File_list[1].path!!)
            val originalFile3 = File(request_Image_File_list[2].path!!)

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

            val file1: MultipartBody.Part =
                MultipartBody.Part.createFormData("img1", originalFile1.name, filePart1)
            val file2: MultipartBody.Part =
                MultipartBody.Part.createFormData("img2", originalFile2.name, filePart2)
            val file3: MultipartBody.Part =
                MultipartBody.Part.createFormData("img3", originalFile3.name, filePart3)

            Log.d("test", file1.toString())

            connect_server.makeBoard(file1, file2, file3, data).enqueue(object :
                Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("test", "서버연결 실패")
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    //val board:String = response.body()!!
                    Log.d("test", "서버 연결 성공")
                }
            })

            (activity as MainActivity).close_fragment(this@RequestPage)
        }

    }


}