package com.professionalandroid.apps.capston_meeting.src

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.capston_meeting.MainActivity

open class BaseActivity: AppCompatActivity() {

    fun printLog(test: String){
        Log.d("test", test)
    }

    fun displayImg(context: Context, imgURL: String, view: ImageView){
        GlideApp.with(context)
            .load(imgURL)
            .centerCrop()
            .into(view)
    }

    fun makeToast(toast: String){
        Toast.makeText(this, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
    }
}