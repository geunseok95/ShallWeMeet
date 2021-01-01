package com.professionalandroid.apps.capston_meeting.src.detailPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.detailPage.models.*
import retrofit2.Call
import retrofit2.http.*

interface DetailPageRetrofitInterface {
    @GET("/api/boards/{boardId}/{userId}/")
    fun getDetail(
        @Path("boardId") boardId: Long,
        @Path("userId") userId: Long
    ): Call<DetailResponse>

    @POST("api/match")
    fun apply(
        @Body applyBody: ApplyBody
    ): Call<ApplyResponse>

    // 즐겨찾기 추가
    @POST("/api/bookmark")
    fun addBookmark(
        @Body data: Bookmark
    ): Call<DefaultResponse>

    // 즐겨찾기 제거
    @DELETE("api/bookmark/{userId}/{boardId}/")
    fun deleteBookmark(
        @Path("userId") userId: Long,
        @Path("boardId") boardId: Long
    ): Call<DefaultResponse>

}