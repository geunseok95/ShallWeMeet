package com.professionalandroid.apps.capston_meeting.src.bookmarkPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.applyPage.models.DefaultResponse
import com.professionalandroid.apps.capston_meeting.src.bookmarkPage.models.BookmarkResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface BookmarkPageRetrofitInterface {

    // 즐겨찾기 조회
    @GET("/api/bookmark/{userId}")
    fun getFavorite(
        @Path("userId") userId: Long
    ):Call<List<BookmarkResponse>>

    // 즐겨찾기 제거
    @DELETE("api/bookmark/{userId}/{boardId}/")
    fun deleteBookmark(
        @Path("userId") userId: Long,
        @Path("boardId") boardId: Long
    ): Call<DefaultResponse>
}