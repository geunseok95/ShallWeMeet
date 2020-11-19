package com.professionalandroid.apps.capston_meeting.src.requestPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.requestPage.models.DefaultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface RequestPageRetrofitInterface {
    // 새로운 미팅 만들기
    @Multipart
    @POST("/api/boards")
    fun makeBoard(
        //인풋을 정의하는 곳
        @Part img1: MultipartBody.Part,
        @Part img2: MultipartBody.Part,
        @Part img3: MultipartBody.Part,
        @PartMap data: HashMap<String, RequestBody>
    ): Call<DefaultResponse>
}