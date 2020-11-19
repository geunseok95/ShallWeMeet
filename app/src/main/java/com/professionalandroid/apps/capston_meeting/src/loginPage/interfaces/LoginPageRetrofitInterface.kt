package com.professionalandroid.apps.capston_meeting.src.loginPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.loginPage.models.LoginResponse
import com.professionalandroid.apps.capston_meeting.src.loginPage.models.Verification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginPageRetrofitInterface {
    // 로그인시 이메일 조회
    @POST("/api/users/login")
    fun validate(
        @Body data: Verification
    ): Call<LoginResponse>

}