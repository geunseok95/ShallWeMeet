package com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.checkPage.sendPage.models.SendResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SendPageRetrofitInterface {
    @GET("api/match/sender/{senderId}")
    fun getSend(
        @Path("senderId")   idx: Long
    ):Call<List<SendResponse>>
}