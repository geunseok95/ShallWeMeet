package com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.models.DefaultResponse
import com.professionalandroid.apps.capston_meeting.src.checkPage.receivePage.myDetailPage.models.MyDetailResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface MyDetailPageRetrofitInterface {
    @GET("/api/boards/{boardId}/{userId}/")
    fun getMyDetail(
        @Path("boardId") boardId: Long,
        @Path("userId") userId: Long
    ): Call<MyDetailResponse>

    @DELETE("api/boards/{idx}")
    fun deletePage(
        @Path("idx") idx: Long
    ): Call<DefaultResponse>
}