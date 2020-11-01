package com.professionalandroid.apps.capston_meeting.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap

interface RetrofitService {

    // 게시판 불러오기
    @GET("/api/boards")
    fun requestSearchBoard(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("location") location: String,
        @Query("num_type") num_type: String,
        @Query("age") age: String
    ):Call<List<board>>

    // 필터로 게시판 불러오기
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

    // 즐겨찾기 조회
    @GET("/api/bookmark/{idx}")
    fun getFavorite(
        @Path("idx") idx: String
    ):Call<List<favoriteboard>>

    // 유저 정보 조회
    @GET("/api/users/{idx}/")
    fun getuserinfo(
        @Path("idx") idx: String
    ):Call<user_info>

    // 로그인시 이메일 조회
    @POST("/api/users/login")
    fun checkhMyID(
        @Body email: email
    ):Call<userid>

    // 새로운 미팅 만들기
    @Multipart
    @POST("/api/boards")
    fun makeBoard(
        //인풋을 정의하는 곳
        @Part img1: MultipartBody.Part,
        @Part img2: MultipartBody.Part,
        @Part img3: MultipartBody.Part,
        @PartMap data: HashMap<String, RequestBody>
    ): Call<String>

    // 즐겨찾기 추가
    @POST("/api/bookmark")
    fun addFavorite(
        @Body data:bookmark
    ): Call<favorite>

    // 즐겨찾기 제거
    @DELETE("api/bookmark/{userId}/{boardId}/")
    fun deleteFavorite(
        @Path("userId") userId: String,
        @Path("boardId") boardId: Long
    ): Call<favorite>

    // 회원가입
    @Multipart
    @POST("/api/users")
    fun registernewId(
        @Part img: MultipartBody.Part,
        @PartMap data: HashMap<String, RequestBody>
    ): Call<new_user>
}

