package com.professionalandroid.apps.capston_meeting.src.requestPage

import android.app.DatePickerDialog
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
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.BaseActivity.Companion.img_num
import com.professionalandroid.apps.capston_meeting.src.BaseActivity.Companion.request_Image_File_list
import com.professionalandroid.apps.capston_meeting.src.BaseActivity.Companion.request_Image_list
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.gender
import com.professionalandroid.apps.capston_meeting.src.requestPage.interfaces.RequestPageView
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.fragment_request_page.*
import kotlinx.android.synthetic.main.fragment_request_page.view.*
import okhttp3.*
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class RequestPage : Fragment(), RequestPageView,
    BottomSheetDialog.BottomsheetbuttonItemSelectedInterface, RequestPopUpWindow.MyDialogOKClickedListener {

    lateinit var mbottomsheetdialog: BottomSheetDialog
    lateinit var spinneradapter1: ArrayAdapter<String>
    lateinit var spinneradapter2: ArrayAdapter<String>
    lateinit var spinneradapter3: ArrayAdapter<String>
    var location2Array = arrayOf<String>()
    var location1: String? = null
    var location2: String? = null
    var year: Int? = 0
    var month: Int? = 0
    var date: Int? = 0


    lateinit var mRequestPageService: RequestPageService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val location1Array = resources.getStringArray(R.array.location)
        val num_typeArray = resources.getStringArray(R.array.num_type)
        location2Array = arrayOf("상관없음")
        spinneradapter1 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location1Array)
        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
        spinneradapter3 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, num_typeArray)
        mRequestPageService = RequestPageService(this, context)
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

        val calendar = Calendar.getInstance()
        val calendar2 = Calendar.getInstance().apply{   add(Calendar.DATE, 7)   }
        view.request_calender.setOnClickListener {
            DatePickerDialog(context!!,
                DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
                    year = p1
                    month = p2 + 1
                    date = p3
                    val selectDate = "${month}월 ${date}일"
                    request_calender.text = selectDate
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) ,calendar.get(Calendar.DAY_OF_MONTH)).apply{
                datePicker.minDate = calendar.timeInMillis
                datePicker.maxDate = calendar2.timeInMillis
            }.show()
        }

        // location
        view.request_location1.apply {
            adapter = spinneradapter1
            prompt = "지역을 선택하세요"
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if(spinneradapter1.getItem(p2).equals("상관없음")){
                        location2 = "상관없음"
                        location2Array = arrayOf("상관없음")
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        view.request_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                        }
                    }

                    else if(spinneradapter1.getItem(p2).equals("서울")){
                        location1 = "서울특별시"
                        location2Array = resources.getStringArray(R.array.Seoul)
                        Log.d("test", location2Array.joinToString())
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        view.request_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                        }
                    }
                    else if(spinneradapter1.getItem(p2).equals("광주")){
                        location1 = "광주광역시"
                        location2Array = resources.getStringArray(R.array.Gwangju)
                        Log.d("test", location2Array.joinToString())
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        view.request_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                        }
                    }
                }
            }
        }

        view.request_num_type.apply {
            adapter = spinneradapter3
            prompt = "인원수를 선택하세요"
        }

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

                if (view.request_title.text.isNotEmpty() && view.request_tag1.text.isNotEmpty() && view.request_tag2.text.isNotEmpty() && view.request_tag3.text.isNotEmpty()
                ) {
                    val popup =
                        RequestPopUpWindow(
                            context,
                            this@RequestPage
                        )
                    popup.start("미팅을 만들까요?", 1)
                }
                else{
                    val popup =
                        RequestPopUpWindow(
                            context,
                            this@RequestPage
                        )
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

            data["title"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_title.text.toString()
            )
            data["location1"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_location1.selectedItem.toString()
            )
            data["location2"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_location2.selectedItem.toString()
            )
            data["num_type"] = RequestBody.create(
                MediaType.parse("text/plain"),
                request_num_type.selectedItem.toString()
            )
            data["gender"] = RequestBody.create(
                MediaType.parse("text/plain"),
                gender
            )
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
            data["date"] = RequestBody.create(
                MediaType.parse("text/plain"),
                "${year}-${month}-${date}"
            )
            data["user"] = RequestBody.create(
                MediaType.parse("text/plain"),
                user.toString()
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

            (activity as MainActivity).progressON((activity as MainActivity))
            mRequestPageService.makeMeeting(file1, file2, file3, data)
        }

    }

    override fun makeMeeting() {
        (activity as MainActivity).progressOFF()

        (activity as MainActivity).close_fragment(this@RequestPage)
    }


}