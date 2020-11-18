package com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.interfaces.SendPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.interfaces.SendPageView
import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.models.SendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendPageService(val mSendPageView: SendPageView, context: Context) {
    val mSendPageRetrofitInterface: SendPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(SendPageRetrofitInterface::class.java)

    fun getSend(idx: Long){
        mSendPageRetrofitInterface.getSend(idx).enqueue(object : Callback<List<SendResponse>>{
            override fun onFailure(call: Call<List<SendResponse>>, t: Throwable) {
                Log.d("test", "보낸 요청 받기 실패")
            }

            override fun onResponse(
                call: Call<List<SendResponse>>,
                response: Response<List<SendResponse>>
            ) {
                val body = response.body()
                if (body != null) {
                    if(body.isNotEmpty())
                        mSendPageView.getSend(body)
                }
            }

        })
    }

}