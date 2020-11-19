package com.professionalandroid.apps.capston_meeting.src.registerPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.professionalandroid.apps.capston_meeting.MainActivity
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.requestPage.BottomSheetDialog
import com.professionalandroid.apps.capston_meeting.src.requestPage.RequestPopUpWindow
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import com.professionalandroid.apps.capston_meeting.src.BaseActivity
import com.professionalandroid.apps.capston_meeting.src.registerPage.interfaces.RegisterPageView
import kotlinx.android.synthetic.main.activity_register_page.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import kotlin.collections.HashMap

class RegisterPage : BaseActivity(), RegisterPageView, BottomSheetDialog.BottomsheetbuttonItemSelectedInterface,  RequestPopUpWindow.MyDialogOKClickedListener {

    lateinit var email: String
    lateinit var gender: String

    lateinit var mbottomsheetdialog: BottomSheetDialog
    val mRegisterPageService =  RegisterPageService(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        email = intent.getStringExtra("email")!!
        gender = intent.getStringExtra("gender")!!
        request_Image_File_list.clear()
        val uri = Uri.parse("android.resource://com.professionalandroid.apps.capston_meeting/drawable/happy.jpg")
        request_Image_File_list.add(uri)
        request_Image_list.clear()
        request_Image_list.add(register_image)

        // 이미지 촬영 및 선택
        register_image.apply {
            setOnClickListener {
                img_num = 0
                mbottomsheetdialog =
                    BottomSheetDialog(
                        this@RegisterPage
                    )
                mbottomsheetdialog.show(supportFragmentManager, "bottom_sheet_dialog")
            }
        }

        // location
        val items = resources.getStringArray(R.array.location)
        val spinneradapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        register_location1.adapter = spinneradapter
        register_location1.prompt = "선호 지역을 선택하세요"


        register_btn.apply {
            setOnClickListener {
                if (register_nickname_input.text.isNotEmpty() && register_age_input.text.isNotEmpty() && register_kakao_id_input.text.isNotEmpty()) {
                    val popup =
                        RequestPopUpWindow(
                            context,
                            this@RegisterPage
                        )
                    popup.start("회원가입을 진행할까요?", 1)
                }
                else{
                    val popup =
                        RequestPopUpWindow(
                            context,
                            this@RegisterPage
                        )
                    popup.start("내용을 모두 입력해주세요", 0)
                }
            }
        }
    }

    override fun bottombutton_listener(v: View) {
        val bottom_sheet_dialog_camera = v.bottom_sheet_dialog_camera
        val bottom_sheet_dialog_gallery = v.bottom_sheet_dialog_gallery

        bottom_sheet_dialog_gallery.setOnClickListener {
            getPhotoFromMyGallary()
        }

        bottom_sheet_dialog_camera.setOnClickListener {
            takeCapture()
        }
    }

    override fun onOKClicked(success: Int) {
        if(success == 1){
            val data: HashMap<String, RequestBody> = HashMap()
            val retrofitService = ConnectRetrofit(this)
            val connect_server = retrofitService.retrofitService()

            data["nickName"] = RequestBody.create(
                MediaType.parse("text/plain"),
                register_nickname_input.text.toString()
            )
            data["age"] = RequestBody.create(
                MediaType.parse("text/plain"),
                register_age_input.text.toString()
            )
            data["email"] = RequestBody.create(
                MediaType.parse("text/plain"),
                email
            )
            data["kakao_id"] = RequestBody.create(
                MediaType.parse("text/plain"),
                register_kakao_id_input.text.toString()
            )
            data["gender"] = RequestBody.create(
                MediaType.parse("text/plain"),
                gender
            )
            data["location1"] = RequestBody.create(
                MediaType.parse("text/plain"),
                register_location1.selectedItem.toString()
            )

            data["location2"] = RequestBody.create(
                MediaType.parse("text/plain"),
                register_location2.selectedItem.toString()
            )

            val originalFile1 = File(request_Image_File_list[0].path!!)

            val filePart1: RequestBody = RequestBody.create(
                MediaType.parse("image/*"),
                originalFile1
            )

            val file1: MultipartBody.Part =
                MultipartBody.Part.createFormData("img", originalFile1.name, filePart1)

            mRegisterPageService.register(file1, data)

        }
    }

    override fun register(idx: Long) {
        val intent = Intent(this@RegisterPage, MainActivity::class.java)
        intent.putExtra("user", idx)
        startActivity(intent)
    }
}


