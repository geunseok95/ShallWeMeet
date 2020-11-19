package com.professionalandroid.apps.capston_meeting.src.requestPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.requestPage.interfaces.RequestPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.requestPage.interfaces.RequestPageView
import com.professionalandroid.apps.capston_meeting.src.requestPage.models.DefaultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestPageService(val mRequestPageView: RequestPageView, val context: Context) {
    val mRequestPageRetrofitInterface: RequestPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(RequestPageRetrofitInterface::class.java)

    fun makeMeeting(img1: MultipartBody.Part, img2: MultipartBody.Part, img3: MultipartBody.Part, data: HashMap<String, RequestBody>){
        mRequestPageRetrofitInterface.makeBoard(img1, img2, img3, data).enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("test", "방만들기 실패")
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body?.code == 200){
                    mRequestPageView.makeMeeting()
                }
            }

        })
    }
}