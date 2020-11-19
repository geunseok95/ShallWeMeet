package com.professionalandroid.apps.capston_meeting.src.successPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.successPage.models.SuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SuccessPageRetrofitInterface {
    @GET("api/match/matched/{makerId}")
    fun getSuccess(
        @Path("makerId") idx: Long
    ): Call<SuccessResponse>
}