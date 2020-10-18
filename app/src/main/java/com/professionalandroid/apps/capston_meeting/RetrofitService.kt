package com.professionalandroid.apps.capston_meeting

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap

interface RetrofitService {

    // 게시판 불러오기
    @GET("/api/boards")
    fun requestSearchBoard(
    ):Call<boards>

    @GET("/filter")
    fun requestSearchBoard2(
        @Query("location") location: String,
        @Query("num_type") num_type: String,
        @Query("age") age: String
    ):Call<List<board>>

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
    @Multipart
    @POST("/api/boards/upload")
    fun sendBoard(
        //인풋을 정의하는 곳
        @Part img1: MultipartBody.Part,
        @Part img2: MultipartBody.Part,
        @Part img3: MultipartBody.Part
        //@Part data: HashMap<String, Any>
        //@Part data: ResponseBody
    ): Call<String>
}