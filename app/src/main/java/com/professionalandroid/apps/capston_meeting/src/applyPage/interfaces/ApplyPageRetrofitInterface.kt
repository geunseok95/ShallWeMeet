package com.professionalandroid.apps.capston_meeting.src.applyPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.applyPage.models.ApplyResponse
import com.professionalandroid.apps.capston_meeting.src.applyPage.models.DefaultResponse
import com.professionalandroid.apps.capston_meeting.src.applyPage.models.Bookmark
import retrofit2.Call
import retrofit2.http.*

interface ApplyPageRetrofitInterface {
    // 게시판 불러오기
    @GET("/api/boards")
    fun requestSearchBoard(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("location1") location1: String,
        @Query("location2") location2: String,
        @Query("num_type") num_type: String,
        @Query("age") age: String,
        @Query("userId") userId: Long,
        @Query("date") date: String,
        @Query("gender") gender: String
    ): Call<List<ApplyResponse>>

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