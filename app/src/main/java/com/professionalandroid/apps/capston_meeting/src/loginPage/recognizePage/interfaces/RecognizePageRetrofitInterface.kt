package com.professionalandroid.apps.capston_meeting.src.loginPage.recognizePage.interfaces

import com.professionalandroid.apps.capston_meeting.src.loginPage.recognizePage.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecognizePageRetrofitInterface {
    @GET("api/check/sendSMS")
    fun getCode(
        @Query("phone") phone: String
    ): Call<DefaultResponse>

    @GET("api/check/checkSMS")
    fun confirmCode(
        @Query("token") token: String
    ): Call<DefaultResponse>

}