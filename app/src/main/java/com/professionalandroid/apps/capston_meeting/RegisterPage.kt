package com.professionalandroid.apps.capston_meeting

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.FileProvider
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_File_list
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.request_Image_list
import com.professionalandroid.apps.capston_meeting.requestPage.BottomSheetDialog
import com.professionalandroid.apps.capston_meeting.requestPage.RequestPopUpWindow
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import com.professionalandroid.apps.capston_meeting.retrofit.user4
import kotlinx.android.synthetic.main.activity_register_page.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class RegisterPage : AppCompatActivity(), BottomSheetDialog.BottomsheetbuttonItemSelectedInterface,  RequestPopUpWindow.MyDialogOKClickedListener {

    companion object{
        lateinit var new_id:String
    }
    private val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_PICK = 10
    lateinit var mbottomsheetdialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        new_id = intent.getStringExtra("new_id")!!
        request_Image_File_list.clear()
        val uri = Uri.parse("android.resource://com.professionalandroid.apps.capston_meeting/drawable/happy.jpg")
        request_Image_File_list.add(uri)
        request_Image_list.clear()
        request_Image_list.add(register_image)


        register_image.apply {
            setOnClickListener {
                MainActivity.img_num = 0
                mbottomsheetdialog =
                    BottomSheetDialog(
                        this@RegisterPage
                    )
                mbottomsheetdialog.show(supportFragmentManager, "bottom_sheet_dialog")
            }
        }

        register_btn.apply {

            setOnClickListener {

                if (register_nickname_input.text.isNotEmpty() && register_age_input.text.isNotEmpty()) {
                    val popup = RequestPopUpWindow(context, this@RegisterPage)
                    popup.start("회원가입을 진행할까요?", 1, new_id)

                }
                else{
                    val popup = RequestPopUpWindow(context, this@RegisterPage)
                    popup.start("내용을 모두 입력해주세요", 0, new_id)
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

    override fun onOKClicked(success: Int, id: String) {
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
                register_age_input.text.toString())

            val originalFile1 = File(request_Image_File_list[0].path!!)

            val filePart1: RequestBody = RequestBody.create(
                MediaType.parse("image/*"),
                originalFile1
            )

            Log.d("test", filePart1.toString())

            val file1: MultipartBody.Part =
                MultipartBody.Part.createFormData("img1", originalFile1.name, filePart1)

            Log.d("test", file1.toString())

            Log.d("test", new_id)

            connect_server.registernewId(data, new_id).enqueue(object :
                Callback<user4> {
                override fun onFailure(call: Call<user4>, t: Throwable) {
                    Log.d("test", "서버연결 실패")
                    Log.d("test in retrofit2", new_id)
                }

                override fun onResponse(call: Call<user4>, response: Response<user4>) {
                    //val board:String = response.body()!!
                    //Log.d("testbody",board)
                    Log.d("test", "서버 연결 성공")
                    val intent = Intent(this@RegisterPage, MainActivity::class.java)
                    Log.d("test in retrofit2", new_id)
                    intent.putExtra("id", new_id)
                    startActivity(intent)
                }
            })
        }
    }


    fun getPhotoFromMyGallary() {
        Intent(Intent.ACTION_PICK).apply{
            type = "image/*"
            startActivityForResult(this, MainActivity.REQUEST_IMAGE_PICK)
        }
    }

    fun takeCapture() {
        //기본 카메라 앱 실행
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile : File? = try{
                    createImageFile()
                }catch (e:Exception){
                    null
                }
                photoFile?.also {
                    val photoURI : Uri = FileProvider.getUriForFile(
                        this,
                        "com.professionalandroid.apps.capston_meeting.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, RegisterPage().REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun createImageFile(): File {
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_",".jpeg",storageDir).apply {
            MainActivity.currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val bitmap : Bitmap
            val file = File(MainActivity.currentPhotoPath)
            if(Build.VERSION.SDK_INT < 28){//안드로이드 9.0 보다 버전이 낮을 경우
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver,Uri.fromFile(file))
                request_Image_list[MainActivity.img_num].setImageBitmap(bitmap)
                request_Image_File_list[MainActivity.img_num] = Uri.fromFile(file)
                Log.d("test", Uri.fromFile(file).toString())

            }else{//안드로이드 9.0 보다 버전이 높을 경우
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    Uri.fromFile(file)
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                request_Image_list[MainActivity.img_num].setImageBitmap(bitmap)
                request_Image_File_list[MainActivity.img_num] = Uri.fromFile(file)
                Log.d("test", Uri.fromFile(file).toString())
            }
        }

        if(requestCode == MainActivity.REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK){
            val bitmap : Bitmap
            if(Build.VERSION.SDK_INT < 28){//안드로이드 9.0 보다 버전이 낮을 경우
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver,data?.data)
                request_Image_File_list[MainActivity.img_num] = Uri.parse("file://"+ getPath(data?.data!!) )
                Log.d("test", "android 9.0 미만 ${request_Image_File_list[MainActivity.img_num]}")
                request_Image_list[MainActivity.img_num].setImageBitmap(bitmap)
            }
            else if(Build.VERSION.SDK_INT >= 29) {//안드로이드 10.0 보다 버전이 높은 경우
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    data?.data!!
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                request_Image_File_list[MainActivity.img_num] =  Uri.fromFile(createCopy(this, data.data!!))
                request_Image_list[MainActivity.img_num].setImageBitmap(bitmap)
            }
            else{//안드로이드 9.0 인 경우
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    data?.data!!
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                request_Image_File_list[MainActivity.img_num] =  Uri.parse("file://" + getPath(data.data!!))
                Log.d("안드로이드 9.0 test", "${request_Image_File_list[MainActivity.img_num]}")

                request_Image_list[MainActivity.img_num].setImageBitmap(bitmap)
            }
        }
    }

    fun getPath(uri: Uri): String?{
        val result: String?
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)

        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }

        return result
    }

    @Nullable
    fun createCopy(
        @NonNull context: Context, @NonNull uri: Uri
    ): File? {
        val imageUrl = getPath(uri)
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val extension = MimeTypeMap.getFileExtensionFromUrl(imageUrl)
        val mimeType = mimeTypeMap.getMimeTypeFromExtension(extension)

        val mimetype = "." + StringBuffer(mimeType!!).substring(6).toString()
        Log.d("test", mimetype)
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val contentResolver: ContentResolver = context.contentResolver ?: return null
        // Create temporary
        val file = File.createTempFile( "${System.currentTimeMillis()}",mimetype,storageDir).apply {
            MainActivity.currentPhotoPath = absolutePath
        }
        try {
            val inputStream: InputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore: IOException) {
            return null
        }
        return file
    }

}


