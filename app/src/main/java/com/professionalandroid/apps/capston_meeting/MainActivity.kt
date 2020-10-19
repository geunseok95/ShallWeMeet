package com.professionalandroid.apps.capston_meeting

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.professionalandroid.apps.capston_meeting.applyPage.ApplyPage
import com.professionalandroid.apps.capston_meeting.homePage.HomePage
import com.professionalandroid.apps.capston_meeting.homePage.SlideViewPager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG_LIST_HOMEPAGE = "TAG_LIST_HOMEPAGE"
        private val REQUEST_IMAGE_CAPTURE = 1
        lateinit var currentPhotoPath : String //문자열 형태의 사진 경로값 (초기값을 null로 시작하고 싶을 때 - lateinti var)
        val REQUEST_IMAGE_PICK = 10
        val request_Image_list = mutableListOf<ImageView>()
        val request_Image_File_list = mutableListOf<Uri>()
        var img_num = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 상태바 투명하게 만들기
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT

        // 권한 요청
        tedPermission()

        val homepage =
            HomePage()
        val applypage =
            ApplyPage()
        val waitpage = WaitPage()
        val successpage = SuccessPage()
        val myinfo = MyInfo()
        val mfragment =
            SlideViewPager()

        val ft = supportFragmentManager

        ft.beginTransaction().replace(R.id.main_layout, homepage).commitAllowingStateLoss()

        bottom_navigation_bar.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.navigation_bar_home -> ft.beginTransaction().replace(R.id.main_layout, homepage).commitAllowingStateLoss()
                R.id.navigation_bar_apply -> ft.beginTransaction().replace(R.id.main_layout, applypage).commitAllowingStateLoss()
                R.id.navigation_bar_favorite -> ft.beginTransaction().replace(R.id.main_layout, waitpage).commitAllowingStateLoss()
                R.id.navigation_bar_success -> ft.beginTransaction().replace(R.id.main_layout, successpage).commitAllowingStateLoss()
                R.id.navigation_bar_myinfo -> ft.beginTransaction().replace(R.id.main_layout, myinfo).commitAllowingStateLoss()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("test", "start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("test", "resume")
    }

    fun move_next_fragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun close_fragment(fragment: Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
        manager.popBackStack()
    }

    fun replace_fragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.new_meeting, fragment)
        transaction.commit()
    }

    // 권한 요청
    private fun tedPermission(){
        val permission = object : PermissionListener{
            override fun onPermissionGranted() {//설정해 놓은 위험권한(카메라 접근 등)이 허용된 경우 이곳을 실행
                Toast.makeText(this@MainActivity,"요청하신 권한이 허용되었습니다.",Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                Toast.makeText(this@MainActivity,"요청하신 권한이 거부되었습니다.",Toast.LENGTH_SHORT).show()            }

        }

        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }

    fun getPhotoFromMyGallary() {
        Intent(Intent.ACTION_PICK).apply{
            type = "image/*"
            startActivityForResult(this,REQUEST_IMAGE_PICK)
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
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun createImageFile(): File {
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_",".jpeg",storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val bitmap : Bitmap
            val file = File(currentPhotoPath)
            if(Build.VERSION.SDK_INT < 28){//안드로이드 9.0 보다 버전이 낮을 경우
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver,Uri.fromFile(file))
                request_Image_list[img_num].setImageBitmap(bitmap)
                request_Image_File_list[img_num] = Uri.fromFile(file)
                Log.d("test", Uri.fromFile(file).toString())

            }else{//안드로이드 9.0 보다 버전이 높을 경우
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    Uri.fromFile(file)
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                request_Image_list[img_num].setImageBitmap(bitmap)
                request_Image_File_list[img_num] = Uri.fromFile(file)
                Log.d("test", Uri.fromFile(file).toString())
            }
        }

        if(requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK){
            val bitmap : Bitmap
            if(Build.VERSION.SDK_INT < 28){//안드로이드 9.0 보다 버전이 낮을 경우
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver,data?.data)
                request_Image_File_list[img_num] = Uri.parse("file://"+ getPath(data?.data!!) )
                Log.d("test", "android 9.0 미만 ${request_Image_File_list[img_num]}")
                request_Image_list[img_num].setImageBitmap(bitmap)
            }
            else if(Build.VERSION.SDK_INT >= 29) {//안드로이드 10.0 보다 버전이 높은 경우
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    data?.data!!
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                request_Image_File_list[img_num] =  Uri.fromFile(createCopy(this, data.data!!))
                request_Image_list[img_num].setImageBitmap(bitmap)
            }
            else{//안드로이드 9.0 인 경우
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    data?.data!!
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                request_Image_File_list[img_num] =  Uri.parse("file://" + getPath(data.data!!))
                Log.d("안드로이드 9.0 test", "${request_Image_File_list[img_num]}")

                request_Image_list[img_num].setImageBitmap(bitmap)
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
            currentPhotoPath = absolutePath
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