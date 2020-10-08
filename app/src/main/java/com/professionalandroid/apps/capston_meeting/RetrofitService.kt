package com.professionalandroid.apps.capston_meeting

import android.graphics.Bitmap
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    // 게시판 불러오기
    @GET("/api/boards")
    fun requestSearchBoard(
    ):Call<boards>

    // 번호로 특정 게시판 불러오기
    @GET("/api/boards/{new_href}/")
    fun requestSearchSpecificBoard(
        @Path("new_href") new_href: String
    ): Call<board>

    // 특정 게시판 사진불러오기
    @GET("/test/{image_path}")
    fun requestSearchImage(
        @Path("image_path") image_path: String
    ): Call<ResponseBody>

    //새로운 글 작성
    @FormUrlEncoded
    @POST("/api/boards/")
    fun requestAdd(
        //인풋을 정의하는 곳
        @Field("title") title:String,
        @Field("subTitle") subTitle:String,
        @Field("content") content:String,
        @Field("createdDate") createdDate:String,
        @Field("updatedDate") updatedDate:String
    ): Call<boards>

}