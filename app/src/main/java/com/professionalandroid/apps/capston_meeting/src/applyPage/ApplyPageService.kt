package com.professionalandroid.apps.capston_meeting.src.applyPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.applyPage.interfaces.ApplyPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.applyPage.interfaces.ApplyPageView
import com.professionalandroid.apps.capston_meeting.src.applyPage.models.ApplyResponse
import com.professionalandroid.apps.capston_meeting.src.applyPage.models.DefaultResponse
import com.professionalandroid.apps.capston_meeting.src.applyPage.models.Bookmark
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyPageService (val mApplyPageView: ApplyPageView, val context: Context) {
    val mApplyPageRetrofitInterface: ApplyPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(ApplyPageRetrofitInterface::class.java)

    fun searchBoard(page:String, size: String, location1: String, location2: String, num_type: String, age: String, userId: Long, date: String, gender: String){
        mApplyPageRetrofitInterface.requestSearchBoard(page, size, location1, location2, num_type, age, userId, date, gender).enqueue(object : Callback<List<ApplyResponse>>{
            override fun onFailure(call: Call<List<ApplyResponse>>, t: Throwable) {
                Log.d("test", "전체목록 불러오기 실패")
            }

            override fun onResponse(
                call: Call<List<ApplyResponse>>,
                response: Response<List<ApplyResponse>>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body != null){
                    if(body.isNotEmpty()){
                        mApplyPageView.loadmore(body)
                    }
                }
            }

        })
    }

    fun addBookmark(date: Bookmark){
        mApplyPageRetrofitInterface.addBookmark(date).enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("test", "즐겨찾기 추가 실패")
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if(response.body()?.code == 200){
                    Log.d("test", "즐겨찾기 추가 성공")
                }
            }

        })
    }

    fun deleteBookmark(userId: Long, boardId: Long){
        mApplyPageRetrofitInterface.deleteBookmark(userId, boardId).enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("test", "즐겨찾기 제거 실패")
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if(response.body()?.code == 200){
                    Log.d("test", "즐겨찾기 제거 성공")
                }
            }

        })
    }
}