package com.professionalandroid.apps.capston_meeting.src.myInfoPage.modifyMyInfoPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.modifyMyInfoPage.interfaces.ModifyMyInfoPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.modifyMyInfoPage.interfaces.ModifyMyInfoPageView
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.modifyMyInfoPage.models.ModifyMyInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyMyInfoPageService(val mModifyMyInfoPageView: ModifyMyInfoPageView, val context: Context) {
    val mModifyMyInfoPageRetrofitInterface: ModifyMyInfoPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(ModifyMyInfoPageRetrofitInterface::class.java)

    fun modifyMyInfo(idx: Long, img: MultipartBody.Part, data: HashMap<String, RequestBody>){
        mModifyMyInfoPageRetrofitInterface.modifyMyInfo(idx, img, data).enqueue(object : Callback<ModifyMyInfoResponse>{
            override fun onFailure(call: Call<ModifyMyInfoResponse>, t: Throwable) {
                Log.d("test", "내 정보 수정 실패")
            }

            override fun onResponse(
                call: Call<ModifyMyInfoResponse>,
                response: Response<ModifyMyInfoResponse>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body?.code == 200){
                    mModifyMyInfoPageView.modifyMyInfo()
                }
            }

        })
    }
}