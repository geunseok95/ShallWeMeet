package com.professionalandroid.apps.capston_meeting.src.registerPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.registerPage.interfaces.RegisterPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.registerPage.interfaces.RegisterPageView
import com.professionalandroid.apps.capston_meeting.src.registerPage.models.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPageService(val mRegisterPageView: RegisterPageView, val context: Context) {
    val mRegisterPageRetrofitInterface: RegisterPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(RegisterPageRetrofitInterface::class.java)

    fun register(img1: MultipartBody.Part, data: HashMap<String, RequestBody>){
        mRegisterPageRetrofitInterface.register(img1, data).enqueue(object : Callback<RegisterResponse>{
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("test", "회원가입 실패")
            }

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val body = response.body()
                Log.d("test", body.toString())
                if(body?.code == 200){
                    mRegisterPageView.register(body.idx)
                }
            }

        })
    }
}