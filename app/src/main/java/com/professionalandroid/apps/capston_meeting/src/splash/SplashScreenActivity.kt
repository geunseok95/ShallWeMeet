package com.professionalandroid.apps.capston_meeting.src.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.loginPage.LoginPage


class SplashScreenActivity:AppCompatActivity() {
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
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginPage::class.java).apply {
                putExtra("fmc_token", token)
            }
            startActivity(intent)
            finish()

        })

    }

}