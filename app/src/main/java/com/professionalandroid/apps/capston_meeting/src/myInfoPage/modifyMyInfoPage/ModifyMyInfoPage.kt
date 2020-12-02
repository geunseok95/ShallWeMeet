package com.professionalandroid.apps.capston_meeting.src.myInfoPage.modifyMyInfoPage

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.BaseActivity.Companion.img_num
import com.professionalandroid.apps.capston_meeting.src.BaseActivity.Companion.request_Image_File_list
import com.professionalandroid.apps.capston_meeting.src.BaseActivity.Companion.request_Image_list
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.modifyMyInfoPage.interfaces.ModifyMyInfoPageView
import com.professionalandroid.apps.capston_meeting.src.requestPage.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.fragment_modify_my_info_page.*
import kotlinx.android.synthetic.main.fragment_modify_my_info_page.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ModifyMyInfoPage : Fragment(), ModifyMyInfoPageView, BottomSheetDialog.BottomsheetbuttonItemSelectedInterface {

    lateinit var mModifyMyInfoPageService: ModifyMyInfoPageService

    lateinit var mbottomsheetdialog: BottomSheetDialog
    lateinit var spinneradapter1: ArrayAdapter<String>
    lateinit var spinneradapter2: ArrayAdapter<String>
    var location2Array = arrayOf<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val location1Array = resources.getStringArray(R.array.location)
        location2Array = arrayOf("상관없음")
        spinneradapter1 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location1Array)
        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
        mModifyMyInfoPageService = ModifyMyInfoPageService(this, context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modify_my_info_page, container, false)

        val uri = Uri.parse("android.resource://com.professionalandroid.apps.capston_meeting/drawable/happy.jpg")
        request_Image_File_list.add(uri)
        request_Image_list.clear()
        request_Image_list.add(view.modify_my_info_image)

        if(arguments != null){
            (activity as MainActivity).displayImg(context!!, arguments?.getString("image")!!, view.modify_my_info_image)
            view.modify_my_info_nickname.setText(arguments?.getString("nickName"))
        }

        view.modify_my_info_image.apply {
            setOnClickListener {
                img_num = 0
                mbottomsheetdialog =
                    BottomSheetDialog(
                        this@ModifyMyInfoPage
                    )
                mbottomsheetdialog.show((activity as MainActivity).supportFragmentManager, "bottom_sheet_dialog")
            }
        }

        view.modify_my_info_location1.apply {
            adapter = spinneradapter1
            prompt = "지역을 선택하세요"
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if(spinneradapter1.getItem(p2).equals("상관없음")){
                        location2Array = arrayOf("상관없음")
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        view.modify_my_info_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                        }
                    }

                    else if(spinneradapter1.getItem(p2).equals("서울")){
                        location2Array = resources.getStringArray(R.array.Seoul)
                        Log.d("test", location2Array.joinToString())
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        view.modify_my_info_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                        }
                    }
                    else if(spinneradapter1.getItem(p2).equals("광주")){
                        location2Array = resources.getStringArray(R.array.Gwangju)
                        Log.d("test", location2Array.joinToString())
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        view.modify_my_info_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                        }
                    }
                }
            }
        }

        view.modify_my_info_confirm.setOnClickListener {

            val data: HashMap<String, RequestBody> = HashMap()

            data["nickName"] = RequestBody.create(
                MediaType.parse("text/plain"),
                modify_my_info_nickname.text.toString()
            )
            data["location1"] = RequestBody.create(
                MediaType.parse("text/plain"),
                modify_my_info_location1.selectedItem.toString()
            )
            data["location2"] = RequestBody.create(
                MediaType.parse("text/plain"),
                modify_my_info_location2.selectedItem.toString()
            )
            var file1: MultipartBody.Part? = null

            if(request_Image_File_list[0] != uri) {
                val originalFile1 = File(request_Image_File_list[0].path!!)
                val filePart1: RequestBody = RequestBody.create(
                    MediaType.parse("image/*"),
                    originalFile1
                )
                file1 = MultipartBody.Part.createFormData("img", originalFile1.name, filePart1)
            }
            mModifyMyInfoPageService.modifyMyInfo(user, file1, data)
        }

        return view
    }

    override fun modifyMyInfo() {
        (activity as MainActivity).close_fragment(this)
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