package com.professionalandroid.apps.capston_meeting.src.myInfoPage.models

data class MyInfoResponse (
    val idx: Long,
    val nickName: String,
    val img: String,
    val email: String,
    val gender: String,
    val age: String,
    val location: String,
    val kakao_id: String,
    val point: Long,
    val token: String
)
