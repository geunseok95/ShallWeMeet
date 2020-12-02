package com.professionalandroid.apps.capston_meeting.src.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.BaseActivity
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication.Companion.FMC_TOKEN
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication.Companion.fmc_token
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication.Companion.sSharedPreferences
import com.professionalandroid.apps.capston_meeting.src.loginPage.LoginPage


class SplashScreenActivity:BaseActivity() {
    private val TAG = "test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myToken()

    }

    private fun myToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            fmc_token = token!!

            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        })

    }

}