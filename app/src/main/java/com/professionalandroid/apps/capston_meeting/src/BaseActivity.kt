package com.professionalandroid.apps.capston_meeting.src

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    fun printLog(test: String){
        Log.d("test", test)
    }
}