package com.professionalandroid.apps.capston_meeting.src.myInfoPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.myInfoPage.models.MyInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyInfoPageRetrofitInterface {
    // 유저 정보 조회
    @GET("/api/users/{idx}/")
    fun getInfo(
        @Path("idx") idx: Long
    ): Call<MyInfoResponse>

}