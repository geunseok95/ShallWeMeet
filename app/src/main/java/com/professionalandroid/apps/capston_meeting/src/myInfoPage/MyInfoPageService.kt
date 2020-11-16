package com.professionalandroid.apps.capston_meeting.src.myInfoPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.interfaces.MyInfoPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.interfaces.MyInfoPageView
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.models.MyInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyInfoPageService(val mMyInfoPageView: MyInfoPageView, val context: Context) {
    val mMyInfoPageRetrofitInterface: MyInfoPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(MyInfoPageRetrofitInterface::class.java)

    fun getInfo(idx: Long){
        mMyInfoPageRetrofitInterface.getInfo(idx).enqueue(object : Callback<MyInfoResponse>{
            override fun onFailure(call: Call<MyInfoResponse>, t: Throwable) {
                Log.d("test", "내 정보 불러오기 실패")
            }

            override fun onResponse(
                call: Call<MyInfoResponse>,
                response: Response<MyInfoResponse>
            ) {
                val body = response.body()
                if(body != null){
                    mMyInfoPageView.getInfo(body)
                }
            }

        })
    }
}