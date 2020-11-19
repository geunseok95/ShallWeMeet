package com.professionalandroid.apps.capston_meeting.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap

interface RetrofitService {


    // 로그인시 이메일 조회
    @POST("/api/users/login")
    fun checkhMyID(
        @Body verification: Verification
    ):Call<userid>



}

