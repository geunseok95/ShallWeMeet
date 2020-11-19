package com.professionalandroid.apps.capston_meeting.src.registerPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.registerPage.models.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface RegisterPageRetrofitInterface {
    // 회원가입
    @Multipart
    @POST("/api/users")
    fun register(
        @Part img: MultipartBody.Part,
        @PartMap data: HashMap<String, RequestBody>
    ): Call<RegisterResponse>
}