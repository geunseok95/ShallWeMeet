package com.professionalandroid.apps.capston_meeting.src.homePage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.homePage.interfaces.HomepageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.homePage.interfaces.HomepageView
import com.professionalandroid.apps.capston_meeting.src.homePage.models.RecommendationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomepageService(val mHomepageView: HomepageView, val context: Context) {
    val mHomepageRetrofitInterface: HomepageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(HomepageRetrofitInterface::class.java)

    fun hotTime(idx: Long){
        mHomepageRetrofitInterface.hotTime(idx).enqueue(object : Callback<List<RecommendationResponse>>{
            override fun onFailure(call: Call<List<RecommendationResponse>>, t: Throwable) {
                Log.d("test", "따끈따끈한 미팅 불러오기 실패")
            }

            override fun onResponse(call: Call<List<RecommendationResponse>>, response: Response<List<RecommendationResponse>>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body != null){
                    if(body.isNotEmpty()){
                        mHomepageView.hotTime(body)
                    }
                }
            }

        })
    }

    fun hotNear(idx: Long){
        mHomepageRetrofitInterface.hotNear(idx).enqueue(object : Callback<List<RecommendationResponse>>{
            override fun onFailure(call: Call<List<RecommendationResponse>>, t: Throwable) {
                Log.d("test", "당신 주변의 미팅 불러오기 실패")
            }
            override fun onResponse(
                call: Call<List<RecommendationResponse>>,
                response: Response<List<RecommendationResponse>>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body != null){
                    if(body.isNotEmpty()){
                        mHomepageView.hotNear(body)
                    }
                }
            }

        })
    }

}