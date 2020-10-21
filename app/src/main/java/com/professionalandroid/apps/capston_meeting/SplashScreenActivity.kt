package com.professionalandroid.apps.capston_meeting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.professionalandroid.apps.capston_meeting.kakaoLoginService.LoginPage
import java.util.ArrayList


class SplashScreenActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)

        finish()
    }



}