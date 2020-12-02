package com.professionalandroid.apps.capston_meeting.src.registerPage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.BaseActivity
import com.professionalandroid.apps.capston_meeting.src.requestPage.BottomSheetDialog
import com.professionalandroid.apps.capston_meeting.src.requestPage.RequestPopUpWindow
import com.professionalandroid.apps.capston_meeting.src.BaseActivity.Companion.img_num
import com.professionalandroid.apps.capston_meeting.src.BaseActivity.Companion.request_Image_File_list
import com.professionalandroid.apps.capston_meeting.src.BaseActivity.Companion.request_Image_list
import com.professionalandroid.apps.capston_meeting.src.loginPage.LoginPage
import com.professionalandroid.apps.capston_meeting.src.registerPage.interfaces.RegisterPageView
import kotlinx.android.synthetic.main.activity_register_page.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import kotlin.collections.HashMap

class RegisterPage : BaseActivity(), RegisterPageView, BottomSheetDialog.BottomsheetbuttonItemSelectedInterface,  RequestPopUpWindow.MyDialogOKClickedListener {

    var email: String? = null
    var gender: String? = null
    var phone: String? = null

    lateinit var mbottomsheetdialog: BottomSheetDialog
    lateinit var mRegisterPageService: RegisterPageService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        mRegisterPageService = RegisterPageService(this, this)
        val ft = supportFragmentManager

        email = intent.getStringExtra("email")
        gender = intent.getStringExtra("gender")
        phone = intent.getStringExtra("phone")

        Log.d("test2", "$email $gender $phone")

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
                mbottomsheetdialog.show(ft, "bottom_sheet_dialog")
            }
        }

        // location
        val location1Array = resources.getStringArray(R.array.location)
        var location2Array = arrayOf("상관없음")
        var spinneradapter1 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, location1Array)
        var spinneradapter2 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, location2Array)

        register_location1.apply {
            adapter = spinneradapter1
            prompt = "지역을 선택하세요"
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if(spinneradapter1.getItem(p2).equals("상관없음")){
                        location2Array = arrayOf("상관없음")
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        register_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                        }
                    }

                    else if(spinneradapter1.getItem(p2).equals("서울")){
                        location2Array = resources.getStringArray(R.array.Seoul)
                        Log.d("test", location2Array.joinToString())
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        register_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                        }
                    }
                    else if(spinneradapter1.getItem(p2).equals("광주")){
                        location2Array = resources.getStringArray(R.array.Gwangju)
                        Log.d("test", location2Array.joinToString())
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        register_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                        }
                    }
                }
            }
        }

        register_btn.apply {
            setOnClickListener {
                if (register_nickname_input.text.isNotEmpty() && register_age_input.text.isNotEmpty()) {
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
                email!!
            )
            data["phone"] = RequestBody.create(
                MediaType.parse("text/plain"),
                phone
            )
            data["gender"] = RequestBody.create(
                MediaType.parse("text/plain"),
                gender!!
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
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("user", idx)
            putExtra("gender", gender)
            putExtra("phone", phone)
        }
        intent.putExtra("user", idx)
        startActivity(intent)
        finish()
    }
}


