package com.professionalandroid.apps.capston_meeting.src.loginPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.loginPage.interfaces.LoginPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.loginPage.interfaces.LoginPageView
import com.professionalandroid.apps.capston_meeting.src.loginPage.models.LoginResponse
import com.professionalandroid.apps.capston_meeting.src.loginPage.models.Verification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPageService(val mLoginPageView: LoginPageView, val context: Context) {
    val mLoginPageRetrofitInterface: LoginPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(LoginPageRetrofitInterface::class.java)

    fun validate(data: Verification){
        mLoginPageRetrofitInterface.validate(data).enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("test", "가입유무 확인 실패")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body?.code == 200){
                    if(body._checked){
                        mLoginPageView.successValidation(body)
                    }
                    else{
                        mLoginPageView.failValidation()
                    }
                }
            }

        })
    }
}