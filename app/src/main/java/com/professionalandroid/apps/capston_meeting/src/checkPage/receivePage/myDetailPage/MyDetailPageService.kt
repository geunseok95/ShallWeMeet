package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.interfaces.MyDetailPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.interfaces.MyDetailPageView
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.models.DefaultResponse
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.models.MyDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyDetailPageService(val mMyDetailPageView: MyDetailPageView, val context: Context) {
    val mMyDetailPageRetrofitInterface: MyDetailPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(MyDetailPageRetrofitInterface::class.java)

    fun getMyDetail(boardId: Long, userId: Long){
        mMyDetailPageRetrofitInterface.getMyDetail(boardId, userId).enqueue(object : Callback<MyDetailResponse>{
            override fun onFailure(call: Call<MyDetailResponse>, t: Throwable) {
                Log.d("test", "내가 만든 미팅 불러오기 실패")
            }

            override fun onResponse(
                call: Call<MyDetailResponse>,
                response: Response<MyDetailResponse>
            ) {
                val body = response.body()
                if(body != null){
                    mMyDetailPageView.getMyDetail(body)
                }
            }

        })
    }

    fun deletePage(idx: Long){
        mMyDetailPageRetrofitInterface.deletePage(idx).enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("test", "페이지 삭제 실패")
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val body = response.body()
                if(body?.code == 200){
                    mMyDetailPageView.deletePage()
                }
            }

        })
    }

}