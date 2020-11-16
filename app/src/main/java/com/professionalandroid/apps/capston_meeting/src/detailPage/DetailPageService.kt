package com.professionalandroid.apps.capston_meeting.src.detailPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.detailPage.interfaces.DetailPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.detailPage.interfaces.DetailPageView
import com.professionalandroid.apps.capston_meeting.src.detailPage.models.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPageService(val mDetailPageView: DetailPageView, val context: Context) {
    val mDetailPageRetrofitInterface: DetailPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(DetailPageRetrofitInterface::class.java)

    fun getDetail(userId: Long, boardId: Long){
        mDetailPageRetrofitInterface.getDetail(boardId, userId).enqueue(object : Callback<DetailResponse>{
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("test", "상세페이지 불러오기 실패")
            }

            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                val body = response.body()
                if(body != null){
                    mDetailPageView.getDetail(body)
                }
            }

        })
    }
}