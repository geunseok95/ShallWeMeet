package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.interfaces

import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.DefaultResponse
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.Permit
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.models.ReceiveResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ReceivePageRetrofitInterface {
    @GET("api/match/maker/{makerId}")
    fun getReceive(
        @Path("makerId") idx: Long
    ): Call<List<ReceiveResponse>>

    @PATCH("api/match")
    fun trySuccess(
        @Body permit: Permit
    ): Call<DefaultResponse>

    @PATCH("api/match")
    fun tryPaySuccess(
        @Body permit: Permit
    ): Call<DefaultResponse>
}