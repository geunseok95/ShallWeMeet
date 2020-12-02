package com.professionalandroid.apps.capston_meeting.src.homePage.models

data class RecommendationResponse (
    val idx: Long,
    val title: String,
    val img1: String,
    val img2: String,
    val img3: String,
    val tag1: String,
    val tag2: String,
    val tag3: String,
    val location1: String,
    val location2: String,
    val num_type: String,
    val age: Int,
    val date: String,
    val gender: String,
    val createdDate: String,
    val updatedDate: String,
    val user: User
)

data class User (
    val idx: Long,
    val nickName: String,
    val img: String,
    val email: String,
    val gender: String,
    val age: String,
    val location1: String,
    val location2: String,
    val kakao_id: String,
    val point: Int
)
