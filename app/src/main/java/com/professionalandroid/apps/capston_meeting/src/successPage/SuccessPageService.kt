package com.professionalandroid.apps.capston_meeting.src.successPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.successPage.interfaces.SuccessPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.successPage.interfaces.SuccessPageView
import com.professionalandroid.apps.capston_meeting.src.successPage.models.SuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuccessPageService(val mSuccessPageView: SuccessPageView, val context: Context) {
    val mSuccessPageRetrofitInterface: SuccessPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(SuccessPageRetrofitInterface::class.java)

    fun getSuccess(idx: Long){
        mSuccessPageRetrofitInterface.getSuccess(idx).enqueue(object : Callback<SuccessResponse>{
            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                Log.d("test","성사목록 불러오기 싪패")
            }

            override fun onResponse(
                call: Call<SuccessResponse>,
                response: Response<SuccessResponse>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body != null){
                    if(body.is_makers.isNotEmpty()){
                        mSuccessPageView.getMakers(body.is_makers)
                    }
                    if(body.is_senders.isNotEmpty()){
                        mSuccessPageView.getSenders(body.is_senders)
                    }
                }
            }
        })

    }
}