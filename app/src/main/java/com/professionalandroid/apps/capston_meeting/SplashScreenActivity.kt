package com.professionalandroid.apps.capston_meeting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.capston_meeting.kakaoLoginService.LoginPage


class SplashScreenActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)

        finish()
    }

}