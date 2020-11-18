package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.interfaces.ReceivePageView
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.interfaces.ReceivePageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.DefaultResponse
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.Permit
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.ReceiveResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceivePageService(val mReceivePageView: ReceivePageView, context: Context) {
    val mReceivePagePageRetrofitInterface: ReceivePageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(ReceivePageRetrofitInterface::class.java)

    fun getReceive(idx: Long){
        mReceivePagePageRetrofitInterface.getReceive(idx).enqueue(object : Callback<List<ReceiveResponse>>{
            override fun onFailure(call: Call<List<ReceiveResponse>>, t: Throwable) {
                Log.d("test", "신청한 사람들 불러오기 실패")
            }

            override fun onResponse(
                call: Call<List<ReceiveResponse>>,
                response: Response<List<ReceiveResponse>>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body != null){
                    if(body.isNotEmpty()){
                        mReceivePageView.getReceive(body)
                    }
                }
            }

        })
    }

    fun trySuccess(permit: Permit, position: Int){
        mReceivePagePageRetrofitInterface.trySuccess(permit).enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("test", "수락하기 실패")
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                when (body?.code) {
                    200 -> {
                        mReceivePageView.success(position)
                    }
                    400 -> {
                        Log.d("test", "이미 매칭 됨")
                    }
                }
            }

        })
    }

    fun tryPaySuccess(permit: Permit, position: Int){
        mReceivePagePageRetrofitInterface.tryPaySuccess(permit).enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("test", "결제하고 수락하기 실패")
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                when(body?.code){
                    200 -> {
                        mReceivePageView.success(position)
                    }
                    401 -> {
                        Log.d("test", "이미 매칭 됨")
                    }
                    402 -> {
                        Log.d("test", "포인트가 부족합니다")

                    }
                }
            }

        })
    }
}