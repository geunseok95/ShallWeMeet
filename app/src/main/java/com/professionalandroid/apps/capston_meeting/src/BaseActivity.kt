package com.professionalandroid.apps.capston_meeting.src

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.FileProvider
import com.professionalandroid.apps.capston_meeting.R
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity: AppCompatActivity() {

    companion object{
        const val TAG_LIST_HOMEPAGE = "TAG_LIST_HOMEPAGE"
        private val REQUEST_IMAGE_CAPTURE = 1
        lateinit var currentPhotoPath : String //문자열 형태의 사진 경로값 (초기값을 null로 시작하고 싶을 때 - lateinti var)
        val REQUEST_IMAGE_PICK = 10
        val request_Image_list = mutableListOf<ImageView>()
        val request_Image_File_list = mutableListOf<Uri>()
        var img_num = 0
    }

    var progressDialog: AppCompatDialog? = null

    fun printLog(test: String){
        Log.d("test", test)
    }

    fun displayImg(context: Context, imgURL: String, view: ImageView){
        GlideApp.with(context)
            .load(imgURL)
            .centerCrop()
            .into(view)
        view.contentDescription = imgURL
    }

    fun makeToast(toast: String){
        Toast.makeText(baseContext, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
    }

    fun getPhotoFromMyGallary() {
        Intent(Intent.ACTION_PICK).apply{
            type = "image/*"
            startActivityForResult(this, REQUEST_IMAGE_PICK)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
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
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
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

    // progress loading
    open fun progressON(activity: Activity?): Unit {
        if (activity == null || activity.isFinishing) {
            return
        }

        if (progressDialog != null && progressDialog!!.isShowing) {
            return
        } else {
            progressDialog = AppCompatDialog(activity)
            progressDialog?.setCancelable(false)
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog?.setContentView(R.layout.layout_progress_loading)
            progressDialog?.show()
        }
        val img_loading_frame = progressDialog?.findViewById<ImageView>(R.id.iv_progress_loading)
        GlideApp.with(this)
            .load(R.raw.progress_loading)
            .into(img_loading_frame!!)
    }


    open fun progressOFF() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog?.dismiss()
        }
    }

}