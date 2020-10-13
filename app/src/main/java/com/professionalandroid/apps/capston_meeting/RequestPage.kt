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
import android.widget.ImageView
import com.makeramen.roundedimageview.RoundedDrawable
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.img_num
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_File_list
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_list
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.fragment_detail_page.view.*
import kotlinx.android.synthetic.main.fragment_request_page.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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
val input: HashMap<String, RequestBody> = HashMap()
                val connect_server = retrofitService.retrofitService()

                input["title"] = RequestBody.create(MultipartBody.FORM, view.request_title.text.toString())
                input["keyword"] = RequestBody.create(MultipartBody.FORM, view.request_keyword.text.toString())
                input["location"] = RequestBody.create(MultipartBody.FORM, view.request_location.text.toString())
                input["num_type"] = RequestBody.create(MultipartBody.FORM, view.request_num_type.text.toString())
                input["gender"] = RequestBody.create(MultipartBody.FORM, "여자")
                input["user"] = RequestBody.create(MultipartBody.FORM, "22")

                val originalFile = File(request_Image_File_list[0].path)
                val originalFile1 = File(request_Image_File_list[1].path)
                val originalFile2 = File(request_Image_File_list[2].path)

                val filePart: RequestBody = RequestBody.create(
                    MediaType.parse("image/*"),
                    originalFile
                )

                val filePart1: RequestBody = RequestBody.create(
                    MediaType.parse("image/*"),
                    originalFile1
                )

                val filePart2: RequestBody = RequestBody.create(
                    MediaType.parse("image/*"),
                    originalFile2
                )

                val file: MultipartBody.Part = MultipartBody.Part.createFormData("img1", originalFile.name, filePart)
                val file1: MultipartBody.Part = MultipartBody.Part.createFormData("img2", originalFile.name, filePart1)
                val file2: MultipartBody.Part = MultipartBody.Part.createFormData("img3", originalFile.name, filePart2)


                val inputimg = listOf<MultipartBody.Part>(file, file1, file2)


                connect_server.sendBoard(inputimg).enqueue(object:
                    Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("test","서버연결 실패")
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val board:String = response.body()!!
                        Log.d("test", "서버 연결 성공")
                        Log.d("test", board)
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


    fun bitmaptoByteArray(img: ImageView): ByteArray{
        val stream = ByteArrayOutputStream()
        val drawable = (img.drawable as BitmapDrawable)
        Log.d("test_img", img.toString())
        Log.d("test_drawable", drawable.toString())
        Log.d("test_drawable_bitmap", drawable.bitmap.toString())
        drawable.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()
        Log.d("test", "$byteArray")
//        for(i in byteArray.indices){
//            Log.d("test", "$i + ${byteArray[i]}")
//        }
        return byteArray
    }

}