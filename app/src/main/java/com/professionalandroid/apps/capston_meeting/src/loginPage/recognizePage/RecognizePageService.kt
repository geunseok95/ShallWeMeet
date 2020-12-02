package com.professionalandroid.apps.capston_meeting.src.loginPage.recognizePage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.loginPage.recognizePage.interfaces.RecognizePageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.loginPage.recognizePage.interfaces.RecognizePageView
import com.professionalandroid.apps.capston_meeting.src.loginPage.recognizePage.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecognizePageService(val mRecognizePageView: RecognizePageView, val context: Context) {
    val mRecognizePageRetrofitInterface: RecognizePageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(RecognizePageRetrofitInterface::class.java)

    fun getCode(phone: String){
        mRecognizePageRetrofitInterface.getCode(phone).enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("test", "인증번호 받기 실패")
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body?.code == 200){
                    mRecognizePageView.getCode()
                }
            }

        })
    }

    fun confirmCode(token: String){
        mRecognizePageRetrofitInterface.confirmCode(token).enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("test", "인증번호 확인 실패")
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body?.code == 200){
                    mRecognizePageView.confirmCode()
                }
            }

        })
    }
}